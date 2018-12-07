package com.app.baselib.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.app.baselib.R;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

/**
 * The type Tab host menu activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 13 :58
 * @描述： TabHostMainActivity
 */
public abstract class TabHostMenuActivity extends AppActivity {

    /**
     * The M view pager.
     */
    public ViewPager mViewPager;
    /**
     * The M indicator.
     */
    public FixedIndicatorView mIndicator;
    /**
     * The M indicator view pager.
     */
    public IndicatorViewPager mIndicatorViewPager;
    /**
     * The M adapter.
     */
    public IndicatorViewPager.IndicatorPagerAdapter mAdapter;
    /**
     * The M scroll bar.
     */
    public ScrollBar mScrollBar;
    /**
     * The M menu drawer.
     */
    public MenuDrawer mMenuDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMenuDrawer = MenuDrawer.attach(this, setMenuPosition());
        if (createContentView() != 0) {
            View view = View.inflate(this, createContentView(), null);
            mMenuDrawer.setContentView(view);
        } else {
            View view = View.inflate(this, R.layout.activity_tabhost, null);
            mMenuDrawer.setContentView(view);
        }
        mMenuDrawer.setMenuView(setMenuView());
        initView();
        initMenuView(mMenuDrawer.getMenuView());
        initData();
    }

    /**
     * Init menu view.初始化view
     *
     * @param menuView
     *         the menu view
     */
    public void initMenuView(View menuView) {

    }

    @Override
    public boolean isCanSwipeBack() {
        return false;
    }

    /**
     * Sets menu view.设置菜单view
     *
     * @return the menu view
     */
    public abstract
    @LayoutRes
    int setMenuView();

    /**
     * Create content view int.创建内容view视图
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

    /**
     * Sets menu position.设置菜单的拉出位置
     *
     * @return the menu position
     */
    public Position setMenuPosition() {
        return Position.LEFT;
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
