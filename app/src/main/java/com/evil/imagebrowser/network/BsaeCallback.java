package com.evil.imagebrowser.network;

import com.app.base.db.ResponseDb;
import com.app.baselib.gson.GsonUtils;
import com.app.baselib.log.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * mvp框架回调
 *
 * @param <T>
 */
public abstract class BsaeCallback<T> implements Callback<T> {
    protected long apiID;
    protected boolean isSaveData = false;//是否保存数据到数据库中

    public BsaeCallback() {
    }

    public BsaeCallback(long apiID) {
        this.apiID = apiID;
        if (apiID > 0) {
            isSaveData = true;
        }
    }

    @Override
    public void onResponse(final Call<T> call,final Response<T> response) {
        if (response.isSuccessful()) {
            if (interceptData()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            T body = response.body();
                            ResponseDb db = new ResponseDb();
                            db.setId(apiID);
                            db.setUrl(call.request().url().toString());
                            String response1 = GsonUtils.toJson(body);
                            db.setResponse(response1);
                            db.setTime(System.currentTimeMillis());
                            db.saveOrUpdate("id = ?",String.valueOf(apiID));
                        } catch (Exception e) {
                            LogUtils.e("noah","保存数据到数据库中-->失败=" + e.getMessage());
                        }
                    }
                }).start();
            }
            T body = response.body();
            if (body != null) {
                onNetSuccess(body);
            } else {
                onNoData(call,response);
            }
        } else {
            onNetError(call,response);
        }
    }

    @Override
    public void onFailure(Call<T> call,Throwable t) {
        if (t != null) {
            LogUtils.e("noah",t.getMessage());
        }
    }

    public abstract void onNetSuccess(T response);

    public void onNoData(Call<T> call,Response<T> response) {}

    public void onNetError(Call<T> call,Response<T> response) { }


    /**
     * 拦截需保存的数据
     *
     * @return
     */
    public boolean interceptData() {
        return isSaveData;
    }
}
