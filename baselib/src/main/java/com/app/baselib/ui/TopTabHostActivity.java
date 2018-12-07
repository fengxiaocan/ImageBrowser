package com.app.baselib.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.app.baselib.R;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

/**
 * The type Top tab host activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 13 :58
 * @描述： TabHostMainActivity
 */
public abstract class TopTabHostActivity extends AppActivity {

    /**
     * The M view pager.
     */
    protected ViewPager mViewPager;
    /**
     * The M indicator.
     */
    protected FixedIndicatorView mIndicator;
    /**
     * The M indicator view pager.
     */
    protected IndicatorViewPager mIndicatorViewPager;
    /**
     * The M adapter.
     */
    protected IndicatorViewPager.IndicatorPagerAdapter mAdapter;
    /**
     * The M scroll bar.
     */
    protected ScrollBar mScrollBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (createContentView() != 0) {
            setContentView(createContentView());
        } else {
            setContentView(R.layout.activity_tabhost_top);
        }
        initView();
        initData();
    }

    @Override
    public boolean isCanSwipeBack() {
        return false;
    }

    /**
     * Create content view int.
     *
     * @return the int
     */
    public
    @LayoutRes
    int createContentView() {
        return 0;
    }

    /**
     * Find view pager view pager.
     *
     * @param id
     *         the id
     *
     * @return the view pager
     */
    public ViewPager findViewPager(int id) {
        return (ViewPager) findViewById(id);
    }

    /**
     * Find indicator view indicator.
     *
     * @param id
     *         the id
     *
     * @return the indicator
     */
    public Indicator findIndicatorView(int id) {
        return (FixedIndicatorView) findViewById(id);
    }

    private void initView() {
        mViewPager = findViewPager(R.id.view_pager);
        mIndicator = (FixedIndicatorView) findIndicatorView(R.id.indicator);
    }

    private void initData() {
        mIndicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
        mAdapter = createPagerAdapter(getSupportFragmentManager());
        mIndicatorViewPager.setAdapter(mAdapter);
        // 设置它可以自定义滑动块的样式
        mScrollBar = createScrollBar();
        if (mScrollBar != null) {
            mIndicatorViewPager.setIndicatorScrollBar(mScrollBar);
        }
        mIndicatorViewPager.setCurrentItem(0, true);
    }

    /**
     * 获取indicatorAdapter
     *
     * @param supportFragmentManager
     *         the support fragment manager
     *
     * @return indicator view pager . indicator pager adapter
     */
    public abstract IndicatorViewPager.IndicatorPagerAdapter createPagerAdapter(FragmentManager supportFragmentManager);

    /**
     * 创建ScrollBar
     *
     * @return scroll bar
     */
    public ScrollBar createScrollBar() {
        return null;
    }
}
