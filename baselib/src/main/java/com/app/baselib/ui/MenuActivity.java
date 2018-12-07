package com.app.baselib.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

/**
 * The type Menu activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 15 :55
 * @描述： 侧滑菜单的activity
 */
public abstract class MenuActivity extends BasicActivity {

    /**
     * The M menu drawer.
     */
    public MenuDrawer mMenuDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMenuDrawer = MenuDrawer.attach(this,setMenuPosition());
        mMenuDrawer.setContentView(setContentView());
        mMenuDrawer.setMenuView(setMenuView());
        initMenuView(mMenuDrawer.getMenuView());
    }

    /**
     * Init menu view.初始化菜单工作
     *
     * @param menuView
     *         the menu view
     */
    public void initMenuView(View menuView){

    }

    /**
     * Sets content view.设置内容的view的id
     *
     * @return the content view
     */
    public abstract
    @LayoutRes
    int setContentView();

    /**
     * Sets menu view.设置菜单View的id
     *
     * @return the menu view
     */
    public abstract
    @LayoutRes
    int setMenuView();

    /**
     * 设置菜单的方向
     *
     * @return menu position
     */
    public Position setMenuPosition() {
        return Position.LEFT;
    }
}
