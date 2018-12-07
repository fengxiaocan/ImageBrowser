package com.app.baselib.intface;

import android.support.v4.view.ViewPager;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;

public interface IIndicatorPager {
    Indicator bindIndicator();

    ViewPager bindViewPager();

    IndicatorViewPager.IndicatorPagerAdapter createIndicatorAdapter();
}
