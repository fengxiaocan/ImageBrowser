package com.app.baselib.intface;

import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.app.baselib.pmsion.CheckPermissionsCallback;
import com.app.baselib.pmsion.RequestPermissionsCallback;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.intface
 * @创建者: Noah.冯
 * @时间: 12:09
 * @描述： TODO
 */

public interface IRequestPermission extends  ActivityCompat.OnRequestPermissionsResultCallback, RequestPermissionsCallback, CheckPermissionsCallback {
    /**
     * 申请权限
     *
     * @param requestCode 申请码
     * @param permissions 权限组
     */
    void requestPermissions(int requestCode, @Nullable String[] permissions);

    /**
     * 申请权限
     *
     * @param requestCode 申请码
     * @param permissions 权限组
     */
    void requestPermissions(int requestCode, @Nullable String permissions);


    /**
     * 申请权限
     *
     * @param requestCode            申请码
     * @param permissions            权限组
     * @param requestPermissionCause 申请权限的原因
     */
    void requestPermissions(int requestCode, @Nullable String permissions, String requestPermissionCause);
}
