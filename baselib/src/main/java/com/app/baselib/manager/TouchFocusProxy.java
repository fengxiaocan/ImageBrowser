package com.app.baselib.manager;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import com.app.baselib.intface.OnLoseFocusListener;
import com.app.baselib.util.KeyboardUtils;
import com.app.baselib.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengxiaocan
 * @desc 一个可以监听焦点消息的工具
 */
public class TouchFocusProxy {
    private boolean isHideKeyboard;//是否隐藏系统键盘
    private boolean isClearFocusOfView;//最终是否需要清除监听失去焦点的View
    private List<Integer> mLoseFocusViewById;//需要监听失去焦点的View的ID
    private List<View> mLoseFocusView;//需要监听失去焦点的View的ID
    private List<Integer> mFilterViewById;//需要过滤忽略的控件
    private List<View> mFilterViews;//需要过滤忽略的控件,即是
    private OnLoseFocusListener mOnLoseFoucsListener;

    private TouchFocusProxy(Builder builder) {
        isHideKeyboard = builder.isHideKeyboard;
        isClearFocusOfView = builder.isClearFocusOfView;
        mLoseFocusViewById = builder.mLoseFocusViewById.size()==0?null:builder.mLoseFocusViewById;
        mLoseFocusView = builder.mLoseFocusView.size()==0?null:builder.mLoseFocusView;
        mFilterViewById = builder.mFilterViewById.size()==0?null:builder.mFilterViewById;
        mFilterViews = builder.mFilterViews.size()==0?null:builder.mFilterViews;
        mOnLoseFoucsListener = builder.mOnLoseFoucsListener;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 是否有需要监视的丢失焦点的控件
     *
     * @return
     */
    private boolean hasMonitorLoseFocus() {
        if (ObjectUtils.isEmpty(mLoseFocusView)) {
            if (ObjectUtils.isEmpty(mLoseFocusViewById)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否有需要过滤的控件
     *
     * @return
     */
    private boolean hasFilterFocus() {
        if (ObjectUtils.isEmpty(mFilterViews)) {
            if (ObjectUtils.isEmpty(mFilterViewById)) {
                return false;
            }
        }
        return true;
    }

    public void onDispatchTouchEvent(Activity activity,MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (!hasMonitorLoseFocus()) {
                return;
            }
            if (isTouchView(activity,mFilterViewById,motionEvent)) {
                return;
            }
            if (isTouchView(mFilterViews,motionEvent)) {
                return;
            }
//            View currentFocusView = activity.getCurrentFocus();
//            if (currentFocusView == null) {
//                dispose(activity,motionEvent);
//                return;
//            }
//            if (isFocusViewInt(currentFocusView,mLoseFocusViewById)) {
//
//            }
            if (isTouchView(activity,mLoseFocusViewById,motionEvent)) {
                return;
            }
//            if (isFocusView(currentFocusView,mLoseFocusView)) {
//
//            }
            //是否触摸在监听View的位置上
            if (isTouchView(mLoseFocusView,motionEvent)) {
                return;
            }
            dispose(activity,motionEvent);
        }
    }

    private void dispose(Activity activity,MotionEvent motionEvent) {
        //隐藏键盘
        if (isHideKeyboard) {
            KeyboardUtils.hideSoftInput(activity);
        }
        if (isClearFocusOfView) {
            clearViewFocus(activity.getCurrentFocus(),mLoseFocusView);
        }
        if (mOnLoseFoucsListener != null) {
            mOnLoseFoucsListener.onLoseFocus(motionEvent);
        }
    }

    /**
     * 清除View的焦点
     *
     * @param v 焦点所在View
     */
    private void clearViewFocusInt(View v,List<Integer> ids) {
        if (ObjectUtils.isEmpty(ids)) {
            return;
        }
        for (int id : ids) {
            if (v.getId() == id) {
                v.clearFocus();
                break;
            }
        }
    }

    /**
     * 清除View的焦点
     *
     * @param v 焦点所在View
     */
    private void clearViewFocus(View v,List<View> views) {
        if (ObjectUtils.isEmpty(views)) {
            return;
        }
        for (View view : views) {
            if (v.getId() == view.getId()) {
                v.clearFocus();
                break;
            }
        }
    }


    //region软键盘的处理

    /**
     * 隐藏键盘
     * 焦点是否在View上
     *
     * @param v 焦点所在View
     * @param ids 输入框
     * @return true代表焦点在View上
     */
    private boolean isFocusViewInt(View v,List<Integer> ids) {
        if (ObjectUtils.isEmpty(ids)) {
            return false;
        }
        for (int id : ids) {
            if (v.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏键盘
     * 焦点是否在View上
     *
     * @param v 焦点所在View
     * @param views 需要监听失去焦点的View
     * @return true代表焦点在View上
     */
    private boolean isFocusView(View v,List<View> views) {
        if (ObjectUtils.isEmpty(views)) {
            return false;
        }
        for (View view : views) {
            if (v.getId() == view.getId()) {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    private boolean isTouchView(List<View> views,MotionEvent motionEvent) {
        if (ObjectUtils.isEmpty(views)) {
            return false;
        }
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (motionEvent.getX() > x &&
                motionEvent.getX() < (x + view.getWidth()) &&
                motionEvent.getY() > y &&
                motionEvent.getY() < (y + view.getHeight()))
            {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    private boolean isTouchView(Activity activities,List<Integer> ids,MotionEvent motionEvent) {
        if (ObjectUtils.isEmpty(ids)) {
            return false;
        }
        int[] location = new int[2];
        for (int id : ids) {
            View view = activities.findViewById(id);
            if (view == null) {
                continue;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (motionEvent.getX() > x &&
                motionEvent.getX() < (x + view.getWidth()) &&
                motionEvent.getY() > y &&
                motionEvent.getY() < (y + view.getHeight()))
            {
                return true;
            }
        }
        return false;
    }

    public static final class Builder {
        private boolean isHideKeyboard;
        private boolean isClearFocusOfView;
        private List<Integer> mLoseFocusViewById = new ArrayList<>();//需要监听失去焦点的View的ID
        private List<View> mLoseFocusView = new ArrayList<>();//需要监听失去焦点的View的ID
        private List<Integer> mFilterViewById = new ArrayList<>();//需要过滤忽略的控件
        private List<View> mFilterViews = new ArrayList<>();//需要过滤忽略的控件,即是
        private OnLoseFocusListener mOnLoseFoucsListener;

        private Builder() {}

        public Builder hideKeyboard(boolean val) {
            isHideKeyboard = val;
            return this;
        }

        public Builder clearFocus(boolean val) {
            isClearFocusOfView = val;
            return this;
        }

        private <T> void add(List<T> mData,T... val) {
            if (ObjectUtils.isEmpty(val)) {
                return;
            }
            for (T t : val) {
                mData.add(t);
            }
        }

        public Builder loseFocusView(Integer... val) {
            add(mLoseFocusViewById,val);
            return this;
        }

        public Builder loseFocusView(Integer val) {
            add(mLoseFocusViewById,val);
            return this;
        }

        public Builder loseFocusView(View... val) {
            add(mLoseFocusView,val);
            return this;
        }

        public Builder loseFocusView(View val) {
            add(mLoseFocusView,val);
            return this;
        }

        public Builder filterView(Integer... val) {
            add(mFilterViewById,val);
            return this;
        }

        public Builder filterView(int val) {
            add(mFilterViewById,val);
            return this;
        }

        public Builder filterView(View... val) {
            add(mFilterViews,val);
            return this;
        }

        public Builder filterView(View val) {
            add(mFilterViews,val);
            return this;
        }

        public Builder loseFoucsListener(OnLoseFocusListener val) {
            mOnLoseFoucsListener = val;
            return this;
        }

        public TouchFocusProxy build() {
            return new TouchFocusProxy(this);
        }
    }
}
