package com.app.baselib.pmsion;

import android.support.annotation.Nullable;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.pmsion
 * @创建者: Noah.冯
 * @时间: 11:37
 * @描述： 检测运行时权限回调接口
 */
public interface CheckPermissionsCallback {
    /**
     * 拥有权限
     */
    void hasPremissions(int requestCode);

    /**
     * 没有权限
     */
    void nonePremissions(int requestCode,@Nullable String[] permissions);
}
