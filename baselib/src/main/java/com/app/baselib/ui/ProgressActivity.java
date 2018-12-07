package com.app.baselib.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.app.baselib.dialog.CommonProgressDialog;
import com.app.baselib.rx.RxEmitter;
import com.app.baselib.rx.RxSubscriber;
import com.app.baselib.rx.RxThread;
import com.app.baselib.rx.SimpleRxAcceptor;

/**
 * The type Handler activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 11 :58
 * @描述： TODO
 */
public class ProgressActivity extends BasicActivity {
    private static boolean isStopDialog = true;
    private CommonProgressDialog mDialog;

    /**
     * 展示加载进度框以及信息
     *
     * @param message the message
     */
    public void showProgress(String message) {
        if (mDialog == null) {
            mDialog = CommonProgressDialog.builderTemp1(this)
                                          .setCancelable(setDialogCancelable())
                                          .setCancelable(setDialogCanceledOnTouchOutside())
                                          .create();
        }
        mDialog.setMessage(message);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 设置进度条是否可以按退回键取消
     *
     * @return boolean
     */
    public boolean setDialogCancelable() {
        return false;
    }

    /**
     * 加载进度框点击外部是否消失
     *
     * @return boolean
     */
    public boolean setDialogCanceledOnTouchOutside() {
        return false;
    }

    /**
     * 取消加载进度框
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * diaolog定时展示消息
     *
     * @param millisTime the millis time
     * @param msg the msg
     */
    public void showProgress(
            final int millisTime,@Nullable final String... msg
    )
    {
        if (msg == null || msg.length == 0) {
            return;
        }
        if (mDialog != null) {
            dismiss();
        }
        isStopDialog = true;
        mDialog = CommonProgressDialog.builderTemp1(this)
                                      .setCancelable(setDialogCancelable())
                                      .setCancelable(setDialogCanceledOnTouchOutside())
                                      .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                          @Override
                                          public void onDismiss(DialogInterface dialog) {
                                              isStopDialog = false;
                                          }
                                      })
                                      .create();
        RxThread.Proxy.io().observeOnMain().open().subscriber(new RxSubscriber<String>() {
            @Override
            public void onSubscribe(RxEmitter<String> emitter) {
                while (isStopDialog) {
                    for (String s : msg) {
                        SystemClock.sleep(millisTime);
                        emitter.onNext(s);
                    }
                }
            }
        }).acceptOnMain().acceptor(new SimpleRxAcceptor<String>() {
            @Override
            public void onStart() {
                mDialog.setMessage(msg[0]);
                mDialog.show();
            }

            @Override
            public void onNext(String s) {
                if (mDialog != null) {
                    mDialog.setMessage(s);
                }
            }
        });
    }

    /**
     * 获取加载dialog
     *
     * @return dialog
     */
    public Dialog getLoadingDialog() {
        return mDialog;
    }
}
