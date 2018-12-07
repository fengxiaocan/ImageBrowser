package com.evil.imagebrowser.network.image;

import android.app.ProgressDialog;
import android.content.Context;

import com.app.baselib.util.ToastUtils;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 19/6/18
 * @desc ...
 */
public class SimpleDownProgress implements DownProgress {
    ProgressDialog dialog;

    public SimpleDownProgress(Context context) {
        dialog = new ProgressDialog(context);
    }

    @Override
    public void start() {
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        dialog.setTitle("数字尾巴");
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("正在加载中");
        dialog.setProgress(0);
        dialog.show();
    }

    @Override
    public void progress(long total,long progress) {
        dialog.setMax((int)total);
        dialog.setProgress((int)progress);
    }

    @Override
    public void complete() {
        dialog.dismiss();
        ToastUtils.showShort("下载完成");
    }

    @Override
    public void error() {
        dialog.dismiss();
        ToastUtils.showShort("下载错误");
    }
}
