package com.app.baselib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.app.baselib.impl.TOnClickListener;
import com.app.baselib.intface.IActivityInit;
import com.app.baselib.intface.IActivityInitWithBack;
import com.app.baselib.intface.IActivityInitWithToolbar;
import com.app.baselib.intface.IActivityRecycle;
import com.app.baselib.intface.IFullScreen;
import com.app.baselib.intface.IIndicatorPager;
import com.app.baselib.intface.IIndicatorPagerImpl;
import com.app.baselib.intface.IRouterInit;
import com.app.baselib.intface.IToolbar;
import com.app.baselib.util.ActivityUtils;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * The type Activity lifecycle adapter.
 * activity 生命周期回调
 */
public class ActivityLifecycleAdapter implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity,Bundle savedInstanceState) {
        if (activity instanceof IFullScreen) {
            ActivityUtils.allScreen(activity);
        }
        if (activity instanceof IRouterInit) {
            ARouter.getInstance().inject(activity);
        }
        boolean isIndicator = false;
        if (activity instanceof IIndicatorPager) {
            isIndicator = true;
        }

        if (activity instanceof IActivityInit) {
            IActivityInit init = (IActivityInit)activity;
            init.setContentView2();
            init.initView();
            if (activity instanceof IActivityInitWithToolbar) {
                IActivityInitWithToolbar init1 = (IActivityInitWithToolbar)activity;
                init1.initToolbar();
            }
            if (activity instanceof IToolbar && activity instanceof AppCompatActivity) {
                IToolbar init1 = (IToolbar)activity;
                int id = init1.toolbarId();
                AppCompatActivity compatActivity = (AppCompatActivity)activity;
                Toolbar toolbar = activity.findViewById(id);
                init1.initToolbar(toolbar);
                compatActivity.setSupportActionBar(toolbar);
                compatActivity.getSupportActionBar().setHomeButtonEnabled(init1.homeButtonEnabled()); // 设置返回键可用
                compatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(init1.homeButtonEnabled());
                init1.afterInitView();
            }
            init.initEvent();
            if (isIndicator) {
                IIndicatorPager pager = (IIndicatorPager)activity;
                Indicator indicator = pager.bindIndicator();
                ViewPager viewPager = pager.bindViewPager();
                IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator,viewPager);
                IndicatorViewPager.IndicatorPagerAdapter adapter = pager.createIndicatorAdapter();
                indicatorViewPager.setAdapter(adapter);
                if (pager instanceof IIndicatorPagerImpl){
                    ((IIndicatorPagerImpl)pager).setIndicator(indicatorViewPager);
                }
            }
            if (activity instanceof IActivityInitWithBack) {
                IActivityInitWithBack init1 = (IActivityInitWithBack)activity;
                View view = activity.findViewById(init1.onBackId());
                if (view != null) {
                    view.setOnClickListener(new TOnClickListener<Activity>(activity) {
                        @Override
                        public void onClick(View v) {
                            t.finish();
                        }
                    });
                }
            }
            init.initData();
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity,Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof IActivityRecycle) {
            ((IActivityRecycle)activity).onRecycle();
        }
    }
}
