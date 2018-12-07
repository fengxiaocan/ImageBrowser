package com.app.baselib.pmsion;

import android.support.annotation.Nullable;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.pmsion
 * @创建者: Noah.冯
 * @时间: 11:37
 * @描述： 请求运行时权限回调接口
 */
public interface RequestPermissionsCallback {
    /**
     * 申请权限成功
     */
    void requestPremissionsSuccess(int requestCode);

    /**
     * 申请权限失败
     */
    void requestPremissionsDefeat(
            int requestCode,@Nullable String[] permissions
    );
}
