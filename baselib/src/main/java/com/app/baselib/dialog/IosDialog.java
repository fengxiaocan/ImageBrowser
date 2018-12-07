package com.app.baselib.dialog;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.baselib.R;

/**
 * Created: AriesHoo on 2017-01-19 14:16
 * Function: 自定义AlertDialog 弹出提示框
 * Desc:
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("InflateParams")
public class IosDialog{

    private Context      context;
    private AlertDialog  dialog;
    private TextView     txt_title;
    private TextView     txt_msg;
    //addView 父容器
    private LinearLayout dialog_Group;
    private LinearLayout linearLayoutMain;
    private LinearLayout linearLayoutGroup;
    private TextView     btn_left;
    private TextView     btn_middle;
    private TextView     btn_right;
    private View         mViewLine;
    private View         mViewLineRight;
    private View         mViewLineHorizontal;
    private boolean showTitle  = false;
    private boolean showMsg    = false;
    private boolean showLayout = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean showNeuBtn = false;

    /**
     * 是否自定义了button样式
     */
    private boolean isCustomButtonStyle = false;

    private int gravity = Gravity.CENTER;
    private Window                     window;
    private WindowManager.LayoutParams lp;

    /**
     * Instantiates a new Ios dialog.
     *
     * @param context
     *         the context
     */
    public IosDialog(Context context){
        this.context = context;
        // 获取Dialog布局
        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.layout_alert_view,null);
        // 获取自定义Dialog布局中的控件
        txt_title = (TextView) view.findViewById(R.id.tv_titleAlertView);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.tv_msgAlertView);
        txt_msg.setVisibility(View.GONE);
        dialog_Group = (LinearLayout) view
                .findViewById(R.id.lLayout_viewAlertView);
        dialog_Group.setVisibility(View.GONE);
        btn_left = (TextView) view.findViewById(R.id.tv_leftAlertView);
        btn_left.setVisibility(View.GONE);
        btn_middle = (TextView) view.findViewById(R.id.tv_middleAlertView);
        btn_middle.setVisibility(View.GONE);
        btn_right = (TextView) view.findViewById(R.id.tv_rightAlertView);
        btn_right.setVisibility(View.GONE);
        mViewLine = view.findViewById(R.id.v_lineAlertView);
        mViewLine.setVisibility(View.GONE);
        mViewLineHorizontal = view.findViewById(R.id.v_lineHorizontalAlertView);
        mViewLineHorizontal.setVisibility(View.GONE);
        mViewLineRight = view.findViewById(R.id.v_lineRightAlertView);
        mViewLineRight.setVisibility(View.GONE);
        linearLayoutGroup = (LinearLayout) view
                .findViewById(R.id.lLayout_groupAlertView);
        linearLayoutMain = (LinearLayout) view
                .findViewById(R.id.lLayout_mainAlertView);
        // 定义Dialog布局和参数
        dialog = new AlertDialog.Builder(context,R.style.AlertViewDialogStyle)
                .create();
        dialog.show();
        dialog.setContentView(view);
        window = dialog.getWindow();
        lp = window.getAttributes();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog){
                dialog_Group.removeAllViews();
            }
        });
        dialog.dismiss();
    }

    /**
     * Builder ios dialog.
     *
     * @return the ios dialog
     */
    public IosDialog builder(){

        return this;
    }

    /**
     * Get dialog alert dialog.
     *
     * @return the alert dialog
     */
    public AlertDialog getDialog(){
        return dialog;
    }

    /**
     * 设置窗口透明度
     *
     * @param alpha
     *         the alpha
     *
     * @return ios dialog
     */
    public IosDialog setAlpha(float alpha){
        lp.alpha = alpha;// 透明度
        window.setAttributes(lp);
        return this;
    }

    /**
     * 设置背景黑暗度
     *
     * @param dimAmount
     *         the dim amount
     *
     * @return ios dialog
     */
    public IosDialog setDimAmount(float dimAmount){
        lp.dimAmount = dimAmount;// 黑暗度
        window.setAttributes(lp);
        return this;
    }

    /**
     * Set content view ios dialog.
     *
     * @param layoutResID
     *         the layout res id
     *
     * @return the ios dialog
     */
    public IosDialog setContentView(int layoutResID){
        dialog.show();
        dialog.setContentView(layoutResID);
        return this;
    }

    /**
     * Set background color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setBackgroundColor(int color){
        linearLayoutMain.setBackgroundColor(color);
        return this;
    }

    /**
     * Set background resource ios dialog.
     *
     * @param resId
     *         the res id
     *
     * @return the ios dialog
     */
    public IosDialog setBackgroundResource(@DrawableRes int resId){
        linearLayoutMain.setBackgroundResource(resId);
        return this;
    }

    /**
     * Set background ios dialog.
     *
     * @param background
     *         the background
     *
     * @return the ios dialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public IosDialog setBackground(Drawable background){
        linearLayoutMain.setBackground(background);
        return this;
    }

    /**
     * 是否设置点击dialog区域外，dialog消失
     *
     * @param cancel
     *         the cancel
     */
    public void setCanceled(boolean cancel){
        if(dialog != null){
            dialog.setCanceledOnTouchOutside(cancel);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     *         the title
     *
     * @return ios dialog
     */
    public IosDialog setTitle(String title){
        if(!title.isEmpty()){
            showTitle = true;
            txt_title.setText(Html.fromHtml(title));
        }
        return this;
    }

    /**
     * Set title ios dialog.
     *
     * @param title
     *         the title
     *
     * @return the ios dialog
     */
    public IosDialog setTitle(int title){
        return setTitle(context.getString(title));
    }

    /**
     * Set title text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setTitleTextColor(int color){
        txt_title.setTextColor(color);
        return this;
    }

    /**
     * Set title text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setTitleTextColor(ColorStateList color){
        txt_title.setTextColor(color);
        return this;
    }

    /**
     * 设置title textSize参考 TextView.setTextSize(unit, textSize)方法
     *
     * @param unit
     *         the unit
     * @param textSize
     *         the text size
     *
     * @return ios dialog
     */
    public IosDialog setTitleTextSize(int unit,float textSize){
        txt_title.setTextSize(unit,textSize);
        return this;
    }

    /**
     * 设置提示语
     *
     * @param msg
     *         the msg
     *
     * @return ios dialog
     */
    public IosDialog setMessage(String msg){
        showMsg = true;
        txt_msg.setText(msg);
        if(msg.contains("<br />")){
            txt_msg.setText(Html.fromHtml(msg));
        }
        txt_msg.post(new Runnable(){
            @Override
            public void run(){
                if(txt_msg.getLineCount() > 4){
                    txt_msg.setMaxWidth((int) context.getResources()
                                                     .getDimension(
                                                             R.dimen.alert_max_width));
                }
                {
                    txt_msg.setMaxWidth((int) context.getResources()
                                                     .getDimension(
                                                             R.dimen.alert_max_width_));
                }
            }
        });
        int padding = (int) context.getResources()
                                   .getDimension(R.dimen.alert_dp_padding);
        txt_msg.setPadding(padding,padding,padding,padding);
        txt_msg.setGravity(gravity);
        return this;
    }

    /**
     * Set message ios dialog.
     *
     * @param msg
     *         the msg
     * @param gravity
     *         the gravity
     *
     * @return the ios dialog
     */
    public IosDialog setMessage(String msg,int gravity){
        this.gravity = gravity;
        return setMessage(msg);
    }

    /**
     * Set message ios dialog.
     *
     * @param msg
     *         the msg
     * @param gravity
     *         the gravity
     *
     * @return the ios dialog
     */
    public IosDialog setMessage(int msg,int gravity){
        this.gravity = gravity;
        return setMessage(msg);
    }

    /**
     * Set message ios dialog.
     *
     * @param msg
     *         the msg
     *
     * @return the ios dialog
     */
    public IosDialog setMessage(int msg){
        return setMessage(context.getString(msg));
    }


    /**
     * Set message text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setMessageTextColor(int color){
        txt_msg.setTextColor(color);
        return this;
    }

    /**
     * Set message text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setMessageTextColor(ColorStateList color){
        txt_msg.setTextColor(color);
        return this;
    }

    /**
     * 设置Message textSize参考 TextView.setTextSize(unit, textSize)方法
     *
     * @param unit
     *         the unit
     * @param textSize
     *         the text size
     *
     * @return ios dialog
     */
    public IosDialog setMessageTextSize(int unit,float textSize){
        txt_msg.setTextSize(unit,textSize);
        return this;
    }

    /**
     * 设置message最低高度
     *
     * @param minHeight
     *         the min height
     *
     * @return ios dialog
     */
    public IosDialog setMessageMinHeight(final int minHeight){
        txt_msg.setMinimumHeight(minHeight);
        return this;
    }

    /**
     * Set min height ios dialog.
     *
     * @param minHeight
     *         the min height
     *
     * @return ios dialog
     */
    public IosDialog setMinHeight(final int minHeight){
        linearLayoutMain.setMinimumHeight(minHeight);
        linearLayoutGroup.setMinimumHeight(minHeight);
        return this;
    }


    /**
     * Set min width ios dialog.
     *
     * @param minWidth
     *         the min width
     *
     * @return the ios dialog
     */
    public IosDialog setMinWidth(final int minWidth){
        linearLayoutMain.setMinimumWidth(minWidth);
        linearLayoutGroup.setMinimumWidth(minWidth);
        return this;
    }

    /**
     * 设置Button textSize参考 TextView.setTextSize(unit, textSize)方法
     *
     * @param unit
     *         the unit
     * @param textSize
     *         the text size
     *
     * @return ios dialog
     */
    public IosDialog setButtonTextSize(int unit,float textSize){
        btn_left.setTextSize(unit,textSize);
        btn_middle.setTextSize(unit,textSize);
        btn_right.setTextSize(unit,textSize);
        return this;
    }

    /**
     * 添加视图
     *
     * @param view
     *         the view
     *
     * @return ios dialog
     */
    public IosDialog setView(View view){
        showLayout = true;
        if(view == null){
            showLayout = false;
        }else{
            dialog_Group.addView(view,
                                 android.view.ViewGroup.LayoutParams
                                         .MATCH_PARENT,
                                 android.view.ViewGroup.LayoutParams
                                         .MATCH_PARENT);
        }
        return this;
    }

    /**
     * 师傅返回键弹框可消失
     *
     * @param cancel
     *         the cancel
     *
     * @return ios dialog
     */
    public IosDialog setCancelable(boolean cancel){
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置左边按钮
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     *
     * @return ios dialog
     */
    public IosDialog setLeftButton(String text,
                                       final IDialogListener
                                               listener){
        showNegBtn = true;
        btn_left.setText(Html.fromHtml(text));
        btn_left.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null){
                    listener.onClick(dialog);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 修改左边button背景
     *
     * @param resId
     *         the res id
     *
     * @return ios dialog
     */
    public IosDialog setLeftButtonBackgroundResource(
            @DrawableRes int resId){
        isCustomButtonStyle = true;
        btn_left.setBackgroundResource(resId);
        return this;
    }

    /**
     * Set negative button background color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setLeftButtonBackgroundColor(@ColorInt int color){
        isCustomButtonStyle = true;
        btn_left.setBackgroundColor(color);
        return this;
    }

    /**
     * Set negative button background ios dialog.
     *
     * @param background
     *         the background
     *
     * @return the ios dialog
     */
    public IosDialog setLeftButtonBackground(Drawable background){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            isCustomButtonStyle = true;
            btn_left.setBackground(background);
        }
        return this;
    }

    /**
     * Set negative button text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setLeftButtonTextColor(int color){
        btn_left.setTextColor(color);
        return this;
    }

    /**
     * Set negative button text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setLeftButtonTextColor(ColorStateList color){
        btn_left.setTextColor(color);
        return this;
    }

    /**
     * Set neutral button ios dialog.
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     *
     * @return the ios dialog
     */
    public IosDialog setMiddleButton(String text,
                                      final IDialogListener
                                              listener){
        showNeuBtn = true;
        btn_middle.setText(Html.fromHtml(text));
        btn_middle.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null){
                    listener.onClick(dialog);
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * Set neutral button ios dialog.
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     *
     * @return the ios dialog
     */
    public IosDialog setMiddleButton(int text,
                                      final IDialogListener
                                              listener){
        return setMiddleButton(context.getString(text),listener);
    }

    /**
     * Set neutral button text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setMiddleButtonTextColor(int color){
        btn_middle.setTextColor(color);
        return this;
    }

    /**
     * Set neutral button text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setMiddleButtonTextColor(ColorStateList color){
        btn_middle.setTextColor(color);
        return this;
    }

    /**
     * 设置中间按钮样式
     *
     * @param background
     *         the background
     *
     * @return ios dialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public IosDialog setMiddleButtonBackground(Drawable background){
        isCustomButtonStyle = true;
        btn_middle.setBackground(background);
        return this;
    }

    /**
     * Set neutral button background resource ios dialog.
     *
     * @param resId
     *         the res id
     *
     * @return the ios dialog
     */
    public IosDialog setMiddleButtonBackgroundResource(@DrawableRes int resId){
        isCustomButtonStyle = true;
        btn_middle.setBackgroundResource(resId);
        return this;
    }

    /**
     * Set neutral button background color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setMiddleButtonBackgroundColor(@ColorInt int color){
        isCustomButtonStyle = true;
        btn_middle.setBackgroundColor(color);
        return this;
    }


    /**
     * 设置右边按钮样式
     *
     * @param background
     *         the background
     *
     * @return ios dialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public IosDialog setRightButtonBackground(Drawable background){
        isCustomButtonStyle = true;
        btn_right.setBackground(background);
        return this;
    }

    /**
     * 设置右边button背景样式
     *
     * @param resId
     *         the res id
     *
     * @return ios dialog
     */
    public IosDialog setRightButtonBackgroundResource(
            @DrawableRes int resId){
        isCustomButtonStyle = true;
        btn_right.setBackgroundResource(resId);
        return this;
    }

    /**
     * Set positive button background color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setRightButtonBackgroundColor(@ColorInt int color){
        isCustomButtonStyle = true;
        btn_right.setBackgroundColor(color);
        return this;
    }

    /**
     * Set on key listener ios dialog.
     *
     * @param onKeyListener
     *         the on key listener
     *
     * @return the ios dialog
     */
    public IosDialog setOnKeyListener(
            DialogInterface.OnKeyListener onKeyListener){
        dialog.setOnKeyListener(onKeyListener);
        return this;
    }

    /**
     * Set on dismiss listener ios dialog.
     *
     * @param onDismissListener
     *         the on dismiss listener
     *
     * @return the ios dialog
     */
    public IosDialog setOnDismissListener(OnDismissListener onDismissListener){
        dialog.setOnDismissListener(onDismissListener);
        return this;
    }

    /**
     * Set negative button ios dialog.
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     *
     * @return the ios dialog
     */
    public IosDialog setLeftButton(int text,
                                       final IDialogListener
                                               listener){
        return setLeftButton(context.getString(text),listener);
    }

    /**
     * Set positive button text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setRightButtonTextColor(int color){
        btn_right.setTextColor(color);
        return this;
    }

    /**
     * Set positive button text color ios dialog.
     *
     * @param color
     *         the color
     *
     * @return the ios dialog
     */
    public IosDialog setRightButtonTextColor(ColorStateList color){
        btn_right.setTextColor(color);
        return this;
    }

    /**
     * Set positive button ios dialog.
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     *
     * @return the ios dialog
     */
    public IosDialog setRightButton(String text,
                                       final IDialogListener listener){
        return setRightButton(text,listener,true);
    }

    /**
     * Set positive button ios dialog.
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     * @param isDismiss
     *         the is dismiss
     *
     * @return the ios dialog
     */
    public IosDialog setRightButton(String text,
                                       final IDialogListener listener,
                                       final boolean isDismiss){
        showPosBtn = true;
        btn_right.setText(Html.fromHtml(text));
        btn_right.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null){
                    listener.onClick(dialog);
                }
                if(isDismiss)
                    dialog.dismiss();
            }
        });
        return this;
    }

    /**
     * 设置右边按钮
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     *
     * @return ios dialog
     */
    public IosDialog setRightButton(int text,
                                       final IDialogListener listener){
        return setRightButton(context.getString(text),listener);
    }

    /**
     * Set positive button ios dialog.
     *
     * @param text
     *         the text
     * @param listener
     *         the listener
     * @param isDismiss
     *         the is dismiss
     *
     * @return the ios dialog
     */
    public IosDialog setRightButton(int text,
                                       final IDialogListener listener,
                                       final boolean isDismiss){
        return setRightButton(context.getString(text),listener,isDismiss);
    }

    private void setLayout(){
        if(showTitle){
            txt_title.setVisibility(View.VISIBLE);
        }
        linearLayoutGroup.setGravity(gravity);
        txt_msg.setGravity(gravity);
        linearLayoutGroup.setGravity(gravity);
        if(showMsg){
            txt_msg.setVisibility(View.VISIBLE);
        }
        if(showLayout){
            dialog_Group.setVisibility(View.VISIBLE);
        }
        if(showPosBtn || showNegBtn || showNeuBtn){//都没有
            mViewLineHorizontal.setVisibility(View.VISIBLE);
        }
        if(isCustomButtonStyle){//设置过自定义样式不再控制
            if(showNegBtn){
                btn_left.setVisibility(View.VISIBLE);
            }
            if(showNeuBtn){
                btn_middle.setVisibility(View.VISIBLE);
            }
            if(showPosBtn){
                btn_right.setVisibility(View.VISIBLE);
            }
            return;
        }
        if(!showPosBtn && showNegBtn && !showNeuBtn){//左一个
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setBackgroundResource(
                    R.drawable.alert_btn_single_selector);
            mViewLine.setVisibility(View.GONE);
            mViewLineRight.setVisibility(View.GONE);
        }else if(showPosBtn && !showNegBtn & !showNeuBtn){//右一个
            btn_right.setVisibility(View.VISIBLE);
            btn_right.setBackgroundResource(
                    R.drawable.alert_btn_single_selector);
            mViewLine.setVisibility(View.GONE);
            mViewLineRight.setVisibility(View.GONE);
        }else if(!showPosBtn && !showNegBtn & showNeuBtn){//中一个
            btn_middle.setVisibility(View.VISIBLE);
            btn_middle.setBackgroundResource(
                    R.drawable.alert_btn_single_selector);
            mViewLine.setVisibility(View.GONE);
            mViewLineRight.setVisibility(View.GONE);
        }else if(showPosBtn && showNegBtn && !showNeuBtn){//左右两个
            btn_right.setVisibility(View.VISIBLE);
            btn_right
                    .setBackgroundResource(R.drawable.alert_btn_right_selector);
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setBackgroundResource(R.drawable.alert_btn_left_selector);
            mViewLine.setVisibility(View.VISIBLE);
            mViewLineRight.setVisibility(View.GONE);
        }else if(!showPosBtn && showNegBtn && showNeuBtn){//左中两个
            btn_middle.setVisibility(View.VISIBLE);
            btn_middle
                    .setBackgroundResource(R.drawable.alert_btn_right_selector);
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setBackgroundResource(R.drawable.alert_btn_left_selector);
            mViewLine.setVisibility(View.VISIBLE);
            mViewLineRight.setVisibility(View.GONE);
        }else if(showPosBtn && !showNegBtn && showNeuBtn){//中右两个
            btn_right.setVisibility(View.VISIBLE);
            btn_right
                    .setBackgroundResource(R.drawable.alert_btn_right_selector);
            btn_middle.setVisibility(View.VISIBLE);
            btn_middle
                    .setBackgroundResource(R.drawable.alert_btn_left_selector);
            mViewLine.setVisibility(View.VISIBLE);
            mViewLineRight.setVisibility(View.GONE);
        }else if(showPosBtn && showNegBtn && showNeuBtn){//三个
            btn_right.setVisibility(View.VISIBLE);
            btn_right
                    .setBackgroundResource(R.drawable.alert_btn_right_selector);
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setBackgroundResource(R.drawable.alert_btn_left_selector);
            btn_middle.setVisibility(View.VISIBLE);
            btn_middle.setBackgroundResource(
                    R.drawable.alert_btn_middle_selector);
            mViewLine.setVisibility(View.VISIBLE);
            mViewLineRight.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Show.
     */
    public void show(){
        setLayout();
        if(!dialog.isShowing())
            dialog.show();
    }

    /**
     * Dismiss.
     */
    public void dismiss(){
        if(dialog.isShowing()){
            dialog.cancel();
            dialog.dismiss();
        }
    }
}
