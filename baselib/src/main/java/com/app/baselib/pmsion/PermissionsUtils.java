package com.app.baselib.pmsion;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.app.baselib.dialog.CommonAlertDialog;
import com.app.baselib.dialog.CommonDialogListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Request permissions utils.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.sss.pmsion
 * @创建者: Noah.冯
 * @时间: 12 :59
 * @描述： 申请权限工具类
 */
public class PermissionsUtils {

    /**
     * 检查是否有权限
     *
     * @param context context
     * @param requestCode 申请码
     * @param permissions 权限组
     * @param callback 检测权限回调
     */
    public static void checkPermissions(
            Context context,
            int requestCode,
            @Nullable String[] permissions,
            CheckPermissionsCallback callback
    )
    {
        if (checkPermissions(context,permissions)) {
            callback.hasPremissions(requestCode);

        } else {
            callback.nonePremissions(requestCode,permissions);
        }
    }

    /**
     * Check permissions boolean.
     * 检测是否有权限
     *
     * @param context the context
     * @param permissions the permissions
     * @return the boolean
     */
    public static boolean checkPermissions(
            Context context,@Nullable String[] permissions
    )
    {
        for (int i = 0;i < permissions.length;i++) {
            if (ContextCompat.checkSelfPermission(context,permissions[i]) !=
                PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }

        //已有权限
        return true;
    }

    /**
     * Check permissions boolean.
     * 检测是否有权限
     *
     * @param context the context
     * @param permissions the permissions
     * @return the boolean
     */
    public static boolean checkPermissions(
            Context context,@Nullable String permissions
    )
    {
        if (ContextCompat.checkSelfPermission(context,permissions) !=
            PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        //已有权限
        return true;
    }


    /**
     * 申请权限
     *
     * @param activity activity
     * @param requestCode 请求码
     * @param permissions 权限组
     */
    public static void requestPermissions(
            Activity activity,int requestCode,@Nullable String[] permissions
    )
    {
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }

    /**
     * 申请权限
     *
     * @param activity activity
     * @param requestCode 请求码
     * @param permission 权限
     */
    public static void requestPermissions(
            Activity activity,int requestCode,@Nullable String permission
    )
    {
        ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
    }

    /**
     * 检测以及申请权限
     *
     * @param activity activity
     * @param requestCode 请求码
     * @param permissions 权限
     * @param callback the callback
     */
    public static void checkAndRequestPermissions(
            Activity activity,
            int requestCode,
            @Nullable String[] permissions,
            RequestPermissionsCallback callback
    )
    {
        for (int i = 0;i < permissions.length;i++) {
            if (ContextCompat.checkSelfPermission(activity,permissions[i]) !=
                PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(activity,requestCode,permissions);
                return;
            }
        }
        //已有权限
        callback.requestPremissionsSuccess(requestCode);
    }


    /**
     * 检测以及申请权限
     *
     * @param activity activity
     * @param requestCode 请求码
     * @param permissions 权限
     * @param callback the callback
     */
    public static void checkAndRequestPermissions(
            Activity activity,
            int requestCode,
            @Nullable String permissions,
            RequestPermissionsCallback callback
    )
    {
        if (ContextCompat.checkSelfPermission(activity,permissions) !=
            PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(activity,requestCode,permissions);
            return;
        }
        //已有权限
        callback.requestPremissionsSuccess(requestCode);
    }

    /**
     * 检测以及申请权限
     *
     * @param activity activity
     * @param requestCode 请求码
     * @param permissions 权限
     * @param callback the callback
     * @param requestPermissonsCause 申请权限原因
     */
    public static void checkAndRequestPermissions(
            Activity activity,
            final int requestCode,
            @Nullable String permissions,
            final RequestPermissionsCallback callback,
            String requestPermissonsCause
    )
    {
        if (isNeedRequestPermissions()) {
            if (ContextCompat.checkSelfPermission(activity,permissions) !=
                PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions)) {
                    CommonAlertDialog.builder(activity)
                                     .setMessage(requestPermissonsCause)
                                     .setCancelable(false)
                                     .setCanceledOnTouchOutside(false)
                                     .setMiddleButton("确定",
                                                      new OnRequestPermissionsListener(activity,
                                                                                     requestCode,
                                                                                     permissions
                                                    )
                                     )
                                     .show();
                } else {
                    requestPermissions(activity,requestCode,permissions);
                }
            } else {
                callback.requestPremissionsSuccess(requestCode);
            }
        } else {
            callback.requestPremissionsSuccess(requestCode);
        }
        //已有权限
    }

    /**
     * 是否需要申请权限
     *
     * @return
     */
    protected static boolean isNeedRequestPermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private static String[] getDeniedPermissions(Context context,String[] permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context,permission) ==
                PackageManager.PERMISSION_DENIED)
            {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions.toArray(new String[deniedPermissions.size()]);
    }

    /**
     * 是否彻底拒绝了某项权限
     */
    public static boolean hasAlwaysDeniedPermission(Context context,String... deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRequestPermissionRationale(context,deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有权限需要说明提示
     */
    public static boolean shouldShowRequestPermissionRationale(
            Context context,String... deniedPermissions
    )
    {
        if (!isNeedRequestPermissions()) {
            return false;
        }
        boolean rationale;
        for (String permission : deniedPermissions) {
            rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                                                                            permission
            );
            if (rationale) {
                return true;
            }
        }
        return false;
    }

    /**
     * 重写回调方法
     */
    protected static class OnRequestPermissionsListener extends CommonDialogListener {

        protected Activity mActivity;
        protected int requestCode;
        protected String permissions;

        public OnRequestPermissionsListener(
                Activity activity,int requestCode,String permissions
        )
        {
            this.mActivity = activity;
            this.requestCode = requestCode;
            this.permissions = permissions;
        }

        @Override
        public void onClick(DialogInterface dialog,int which) {
            requestPermissions(mActivity,requestCode,permissions);
            dialog.dismiss();
        }
    }
}
