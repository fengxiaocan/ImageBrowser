package com.app.baselib.skin.base;

import android.app.Application;

import com.app.baselib.skin.loader.SkinManager;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:54
 * Desc:
 */
public class SkinBaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
