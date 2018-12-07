package com.app.baselib.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.adapter
 * @创建者: Noah.冯
 * @时间: 19:13
 * @描述： TODO
 */

public abstract class TabHostFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{

    private AppCompatActivity mActivity;

    public TabHostFragmentAdapter(AppCompatActivity activity){
        super(activity.getSupportFragmentManager());
        mActivity = activity;
    }

    public abstract void bindView(int position,View convertView);

    public abstract
    @LayoutRes
    int getLayout();

    @Override
    public View getViewForTab(int position,View convertView,ViewGroup container){
        convertView = View.inflate(mActivity,getLayout(),null);
        convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        bindView(position,convertView);
        return convertView;
    }

}
