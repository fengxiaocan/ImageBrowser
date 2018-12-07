package com.app.baselib.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.baselib.dialog.CommonProgressDialog;
import com.app.baselib.intface.IHandler;
import com.app.baselib.pmsion.CheckPermissionsCallback;
import com.app.baselib.pmsion.RequestPermissionsCallback;
import com.app.baselib.util.HandlerUtils;
import com.app.baselib.util.ToastUtils;

import static com.app.baselib.pmsion.PermissionsUtils.checkAndRequestPermissions;
import static com.app.baselib.pmsion.PermissionsUtils.checkPermissions;

/**
 * The type App fragment.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base
 * @创建者: Noah.冯
 * @时间: 14 :17
 * @描述： Fragment基类
 */
public class AppFragment extends Fragment
        implements ActivityCompat.OnRequestPermissionsResultCallback,
                   RequestPermissionsCallback,
                   CheckPermissionsCallback,
                   View.OnClickListener
{

    protected CommonProgressDialog mLoadingDialog;

    @Override
    public void onCreate(
            @Nullable Bundle savedInstanceState
    )
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    )
    {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(
            View view,@Nullable Bundle savedInstanceState
    )
    {
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置点击事件
     *
     * @param view the view
     */
    public void setOnClick(View view) {
        view.setOnClickListener(this);
    }

    /**
     * Set on click.
     *
     * @param views the views
     */
    public void setOnClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     * @param isFinish the is finish
     */
    public void openActivity(
            Class<? extends BasicActivity> clazz,boolean isFinish
    )
    {
        openActivity(clazz,null,isFinish);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     * @param bundle the bundle
     */
    public void openActivity(Class<? extends BasicActivity> clazz,Bundle bundle) {
        openActivity(clazz,bundle,false);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     */
    public void openActivity(Class<? extends BasicActivity> clazz) {
        openActivity(clazz,null,false);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     * @param bundle the bundle
     * @param isFinish the is finish
     */
    public void openActivity(
            Class<? extends BasicActivity> clazz,Bundle bundle,boolean isFinish
    )
    {
        Intent intent = new Intent(getContext(),clazz);
        if (bundle != null) {
            intent.putExtra("Bundle",bundle);
        }
        startActivity(intent);
        if (isFinish) {
            getActivity().finish();
        }
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz the clazz
     * @param bundle the bundle
     * @param requestCode the request code
     */
    public void openActivityForResult(
            Class<? extends BasicActivity> clazz,Bundle bundle,int requestCode
    )
    {
        Intent intent = new Intent(getContext(),clazz);
        if (bundle != null) {
            intent.putExtra("Bundle",bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz the clazz
     * @param requestCode the request code
     */
    public void openActivityForResult(
            Class<? extends BasicActivity> clazz,int requestCode
    )
    {
        openActivityForResult(clazz,null,requestCode);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
    }

    /**
     * 新建一个bundle
     *
     * @return bundle
     */
    public Bundle newBundle() {
        return new Bundle();
    }


    /**
     * 申请权限
     *
     * @param requestCode 申请码
     * @param permissions 权限组
     */
    public void requestPermissions(
            int requestCode,@Nullable String[] permissions
    )
    {
        checkAndRequestPermissions(getActivity(),requestCode,permissions,this);
    }

    /**
     * 申请权限
     *
     * @param requestCode 申请码
     * @param permissions 权限组
     * @param requestPermissionCause 申请权限的原因
     */
    public void requestPermissions(
            int requestCode,@Nullable String permissions,String requestPermissionCause
    )
    {
        checkAndRequestPermissions(getActivity(),
                                   requestCode,
                                   permissions,
                                   this,
                                   requestPermissionCause
        );
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults
    )
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        checkPermissions(getActivity(),requestCode,permissions,this);
    }

    /**
     * 申请权限成功
     *
     * @param requestCode 申请码
     */
    @Override
    public void requestPremissionsSuccess(int requestCode) {

    }

    /**
     * 申请权限失败
     *
     * @param requestCode 申请码
     * @param permissions 权限组
     */
    @Override
    public void requestPremissionsDefeat(
            int requestCode,@Nullable String[] permissions
    )
    {

    }

    /**
     * 有权限
     *
     * @param requestCode 申请码
     */
    @Override
    public void hasPremissions(int requestCode) {
        requestPremissionsSuccess(requestCode);
    }

    /**
     * 没有权限
     *
     * @param requestCode 申请码
     * @param permissions 权限组
     */
    @Override
    public void nonePremissions(
            int requestCode,@Nullable String[] permissions
    )
    {
        requestPremissionsDefeat(requestCode,permissions);
    }

    /**
     * 展示加载进度框以及信息
     *
     * @param message the message
     */
    public void show(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = CommonProgressDialog.builderTemp1(getContext()).create();
        }
        mLoadingDialog.setMessage(message);
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 取消加载进度框
     */
    public void dismiss() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 获取加载dialog
     *
     * @return dialog
     */
    public Dialog getLoadingDialog() {
        return mLoadingDialog;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * Toast.
     *
     * @param msg the msg
     */
    public void toast(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * Toast.
     *
     * @param msg the msg
     */
    public void toast(
            @StringRes int msg
    )
    {
        ToastUtils.showShort(getString(msg));
    }

    /**
     * Register handler.
     *
     * @param handler the handler
     */
    public void registerHandler(IHandler handler) {
        HandlerUtils.registerHandler(handler);
    }

    /**
     * Unregister handler.
     *
     * @param handler the handler
     */
    public void unregisterHandler(IHandler handler) {
        HandlerUtils.unregisterHandler(handler);
    }
}
