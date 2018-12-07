package com.app.baselib.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.app.baselib.util.HandlerUtils;


/**
 * The type Logo activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 17 :19
 * @描述： logo页面
 */
public abstract class LogoActivity extends BasicActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        onCreate();
    }

    /**
     * On create.
     */
    public abstract void onCreate();

    @Override
    protected void onStart(){
        super.onStart();
        HandlerUtils.postDelayed(mRunnable,taskTime());
    }

    @Override
    protected void onStop(){
        super.onStop();
        HandlerUtils.removeCallbacks(mRunnable);
    }

    private Runnable mRunnable = new Runnable(){
        @Override
        public void run(){
            runTask();
        }
    };

    /**
     * 定时任务
     */
    public abstract void runTask();

    /**
     * 定时任务
     *
     * @return long
     */
    public abstract long taskTime();

}
