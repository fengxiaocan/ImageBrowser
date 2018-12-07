package com.app.baselib.mask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.app.baselib.R;

public class RoundedView extends View {

    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    //    private int mBackgroundColor;
    private int mColor;
    private float mRadius;
    private Paint mPaint;

    public RoundedView(Context context) {
        super(context);
        init(null);
    }

    public RoundedView(
            Context context,@Nullable AttributeSet attrs
    )
    {
        super(context,attrs);
        init(attrs);
    }

    public RoundedView(Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundedView(
            Context context,@Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes
    )
    {
        super(context,attrs,defStyleAttr,defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.RoundedView);
        //        mBackgroundColor = ta.getColor(R.styleable.RoundedView_round_background,
        //                                       DEFAULT_BACKGROUND_COLOR
        //        );
        mColor = ta.getColor(R.styleable.RoundedView_round_color,DEFAULT_BACKGROUND_COLOR);
        int alpha = Color.alpha(mColor);
        mRadius = ta.getDimension(R.styleable.RoundedView_round_radiu,0);
        //        mRadius = AdapterUtils.dp2px(getContext(),mRadius);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAlpha(alpha);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //                super.onDraw(canvas);
        canvas.drawRoundRect(new RectF(0,0,getMeasuredWidth(),getMeasuredHeight()),
                             mRadius,
                             mRadius,
                             mPaint
        );
    }
}
