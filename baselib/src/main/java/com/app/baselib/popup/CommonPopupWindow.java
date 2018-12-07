package com.app.baselib.popup;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.app.baselib.util.ViewUtils;


/**
 * Created by MQ on 2017/5/2.
 */
public class CommonPopupWindow extends PopupWindow {

    /**
     * The Controller.
     */
    final PopupController controller;

    private CommonPopupWindow(Context context) {
        controller = new PopupController(context,this);
    }

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    @Override
    public int getWidth() {
        return controller.mPopupView.getMeasuredWidth();
    }

    @Override
    public int getHeight() {
        return controller.mPopupView.getMeasuredHeight();
    }

    @Override
    public void dismiss() {
        try {
            controller.setBackGroundLevel(1.0f);
            controller.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.dismiss();
    }

    /**
     * The interface View interface.
     */
    public interface ViewInterface {

        /**
         * Gets child view.
         *
         * @param view the view
         * @param layoutResId the layout res id
         */
        void getChildView(View view,int layoutResId);
    }

    /**
     * The type Builder.
     */
    public static class Builder {
        private final PopupController.PopupParams params;
        private ViewInterface listener;

        /**
         * Instantiates a new Builder.
         *
         * @param context the context
         */
        public Builder(Context context) {
            params = new PopupController.PopupParams(context);
        }

        /**
         * Sets view.
         *
         * @param layoutResId 设置PopupWindow 布局ID
         * @return Builder view
         */
        public Builder setView(int layoutResId) {
            params.mView = null;
            params.layoutResId = layoutResId;
            return this;
        }

        /**
         * Sets view.
         *
         * @param view 设置PopupWindow布局
         * @return Builder view
         */
        public Builder setView(View view) {
            params.mView = view;
            params.layoutResId = 0;
            return this;
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder view onclick listener
         */
        public Builder setViewOnclickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         *
         * @param width 宽
         * @param height the height
         * @return Builder width and height
         */
        public Builder setWidthAndHeight(int width,int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         * @return Builder back ground level
         */
        public Builder setBackGroundLevel(float level) {
            params.isShowBg = true;
            params.bg_level = level;
            return this;
        }

        /**
         * 是否可点击Outside消失
         *
         * @param touchable 是否可点击
         * @return Builder outside touchable
         */
        public Builder setOutsideTouchable(boolean touchable) {
            params.isTouchable = touchable;
            return this;
        }

        /**
         * 设置动画
         *
         * @param animationStyle the animation style
         * @return Builder animation style
         */
        public Builder setAnimationStyle(int animationStyle) {
            params.isShowAnim = true;
            params.animationStyle = animationStyle;
            return this;
        }

        /**
         * 创建CommonPopupWindow
         * Create common popup window.
         *
         * @return the common popup window
         */
        public CommonPopupWindow create() {
            final CommonPopupWindow popupWindow = new CommonPopupWindow(params.mContext);
            params.apply(popupWindow.controller);
            if (listener != null && params.layoutResId != 0) {
                listener.getChildView(popupWindow.controller.mPopupView,params.layoutResId);
            }
            ViewUtils.measureWidthAndHeight(popupWindow.controller.mPopupView);
            return popupWindow;
        }
    }
}
