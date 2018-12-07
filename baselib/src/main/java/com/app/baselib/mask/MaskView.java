package com.app.baselib.mask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.app.baselib.AdapterUtils;
import com.app.baselib.R;

/**
 * The type Mask view.
 * 一个蒙版的drawable，可以实现圆角，正方形，矩形，圆形的中心镂空的蒙版的类
 */
public class MaskView extends View {
    /**
     * The Default background color.
     */
    protected final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;//默认背景颜色
    /**
     * The M eraser bitmap.
     */
    protected Bitmap mEraserBitmap;
    /**
     * The M eraser canvas.
     */
    protected Canvas mEraserCanvas;
    /**
     * The M eraser.
     */
    protected Paint mEraser;

    /**
     * The M square.
     * 是否是正方形
     */
    protected boolean mSquare;
    /**
     * The M background color.
     * 背景色
     */
    protected int mBackgroundColor;
    /**
     * The M mask color.
     */
    protected int mMaskColor;

    /**
     * The M left.
     */
    protected float mLeft;
    /**
     * The M top.
     */
    protected float mTop;
    /**
     * The M right.
     */
    protected float mRight;
    /**
     * The M bottom.
     */
    protected float mBottom;
    /**
     * The M radius.
     */
    protected float mRadius;
    /**
     * The M rx.
     */
    protected float mRx;
    /**
     * The M ry.
     */
    protected float mRy;

    /**
     * The M mask type.
     */
    protected MaskType mMaskType = MaskType.CIRCLE;//默认为圆形的类型

    /**
     * Instantiates a new Mask view.
     *
     * @param context the context
     */
    public MaskView(Context context) {
        super(context);
        init(null);
    }

    /**
     * Instantiates a new Mask view.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public MaskView(Context context,@Nullable AttributeSet attrs) {
        super(context,attrs);
        init(attrs);
    }

    /**
     * Instantiates a new Mask view.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyleAttr the def style attr
     */
    public MaskView(Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(attrs);
    }

    /**
     * Instantiates a new Mask view.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes the def style res
     */
    @SuppressLint("NewApi")
    public MaskView(Context context,@Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes) {
        super(context,attrs,defStyleAttr,defStyleRes);
        init(attrs);
    }

    /**
     * Init.
     * 初始化参数
     *
     * @param attrs the attrs
     */
    protected void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.MaskView);
        mBackgroundColor = ta.getColor(R.styleable.MaskView_mask_background_color,
                                       DEFAULT_BACKGROUND_COLOR
        );
        int integer = ta.getInteger(R.styleable.MaskView_mask_type,0);
        switch (integer) {
            default:
            case 0:
                mMaskType = MaskType.CIRCLE;
                break;
            case 1:
                mMaskType = MaskType.RECT;
                break;
            case 2:
                mMaskType = MaskType.ROUND_RECT;
                break;
            case 3:
                mMaskType = MaskType.ARC;
                break;
        }
        mSquare = ta.getBoolean(R.styleable.MaskView_mask_square,false);
        mMaskColor = ta.getColor(R.styleable.MaskView_mask_color,0xFFFFFFFF);

        mLeft = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_left,0));
        mTop = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_top,0));
        mRight = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_right,0));
        mBottom = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_bottom,0));
        mRadius = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_radiu,-1));
        mRx = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_center_x,0));
        mRy = AdapterUtils.dp2px(getContext(),ta.getDimension(R.styleable.MaskView_mask_center_y,0));

        ta.recycle();

        setWillNotDraw(false);
        //初始化画笔
        mEraser = new Paint();
        mEraser.setAntiAlias(true);
        mEraser.setColor(mMaskColor);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,mSquare ? widthMeasureSpec : heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed,int left,int top,int right,int bottom) {
        super.onLayout(changed,left,top,right,bottom);
        if (mEraserBitmap != null && !mEraserBitmap.isRecycled()) {
            mEraserBitmap.recycle();
        }
        if (mEraserCanvas != null) {
            mEraserCanvas = null;
        }
        mEraserBitmap = MaskEngine.getInstance()
                                  .getBitmap(mMaskColor);
        mEraserCanvas = new Canvas(mEraserBitmap);
        if (mMaskType == MaskType.CIRCLE) {
            if (mRadius < 0) {
                mRadius = mRx = mRy = Math.min(getMeasuredWidth(),getMeasuredHeight()) / 2;
            }
        } else if (mMaskType == MaskType.ROUND_RECT) {
            if (mRadius < 0) {
                mMaskType = MaskType.RECT;
            } else {
                mRx = mRy = mRadius;
            }
        }
    }

    /**
     * Recycle mask drawable.
     *
     * @return the mask drawable
     */
    public void recycle() {
        if (mEraserBitmap != null && !mEraserBitmap.isRecycled()) {
            mEraserBitmap.recycle();
        }
    }

    /**
     * Is recycled boolean.
     * 是否已释放
     *
     * @return the boolean
     */
    public boolean isRecycled() {
        return mEraserBitmap == null || mEraserBitmap.isRecycled();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mEraserBitmap.eraseColor(mMaskColor);
        mEraserCanvas.drawColor(mBackgroundColor);
        switch (mMaskType) {
            //暂时不开放
            //            case ARC:
            //                mEraserCanvas.drawArc(new RectF(mRx,mRy,mRadius,mRadius),mRx,mRy,false,mEraser);
            //                break;
            case RECT:
                mEraserCanvas.drawRect(new Rect(trant(mLeft),
                                                trant(mTop),
                                                trant(mRight),
                                                trant(mBottom)
                ),mEraser);
                break;
            case ROUND_RECT:
                mEraserCanvas.drawRoundRect(new RectF(mLeft,mTop,mRight,mBottom),mRx,mRy,mEraser);
                break;
            case OVAL:
                mEraserCanvas.drawOval(new RectF(mLeft,mTop,mRight,mBottom),mEraser);
                break;
            default:
            case CIRCLE:
                mEraserCanvas.drawCircle(mRx,mRy,mRadius,mEraser);
                break;
        }

        canvas.drawBitmap(mEraserBitmap,0,0,null);
    }

    private int trant(float i) {
        return (int)i;
    }
}