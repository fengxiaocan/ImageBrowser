package com.app.baselib.mask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;

import static com.app.baselib.mask.MaskType.CIRCLE;
import static com.app.baselib.mask.MaskType.OVAL;
import static com.app.baselib.mask.MaskType.RECT;
import static com.app.baselib.mask.MaskType.ROUND_RECT;

/**
 * The type Mask drawable.
 * 一个蒙版的drawable，可以实现圆角，正方形，矩形，圆形的中心镂空的蒙版
 */
public class MaskDrawable extends ColorDrawable {
    /**
     * The Default mask color.
     */
    static final int DEFAULT_MASK_COLOR = Color.TRANSPARENT;
    /**
     * The Default mask brackground.
     */
    static final int DEFAULT_MASK_BRACKGROUND = Color.WHITE;
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
     * The M mask color.
     */
    protected int mMaskColor = DEFAULT_MASK_COLOR;

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
     * Instantiates a new Mask drawable.
     */
    public MaskDrawable() {
        this(new Rect());
    }

    /**
     * Instantiates a new Mask drawable.
     *
     * @param rect the rect
     */
    public MaskDrawable(Rect rect) {
        super();
        setBounds(rect);
        setColor(DEFAULT_MASK_BRACKGROUND);
        init();
    }

    /**
     * Instantiates a new Mask drawable.
     *
     * @param width the width
     * @param height the height
     */
    public MaskDrawable(int width,int height) {
        this(new Rect(0,0,width,height));
    }

    /**
     * Instantiates a new Mask drawable.
     *
     * @param color the color
     */
    public MaskDrawable(int color) {
        this(new Rect(),color);
    }


    /**
     * Instantiates a new Mask drawable.
     *
     * @param rect the rect
     * @param color the color
     */
    public MaskDrawable(Rect rect,int color) {
        super(color);
        setBounds(rect);
        init();
    }

    /**
     * Instantiates a new Mask drawable.
     *
     * @param width the width
     * @param height the height
     * @param color the color
     */
    public MaskDrawable(int width,int height,int color) {
        this(new Rect(0,0,width,height),color);
    }

    private MaskDrawable(Builder builder) {
        this(builder.width,builder.height,builder.mBackgroundColor);
        mMaskColor = builder.mMaskColor;
        mLeft = builder.mLeft;
        mTop = builder.mTop;
        mRight = builder.mRight;
        mBottom = builder.mBottom;
        mRadius = builder.mRadius;
        mRx = builder.mRx;
        mRy = builder.mRy;
        mMaskType = builder.mMaskType;

        if (mMaskType == MaskType.CIRCLE) {
            if (mRadius < 0) {
                mRadius = mRx = mRy = Math.min(getBounds().width(),getBounds().height()) / 2;
            }
        }
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder builder.
     *
     * @param width the width
     * @param height the height
     * @return the builder
     */
    public static Builder builder(int width,int height) {
        return new Builder(width,height);
    }

    /**
     * Init.
     */
    protected void init() {
        mEraserBitmap = MaskEngine.getInstance().getBitmap(mMaskColor);
        mEraserCanvas = new Canvas(mEraserBitmap);
        mEraser = new Paint();
        mEraser.setAntiAlias(true);
        mEraser.setColor(mMaskColor);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * On draw mask drawable.
     *
     * @param canvas the canvas
     * @return the mask drawable
     */
    public MaskDrawable onDraw(Canvas canvas) {
        draw(canvas);
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        //        super.draw(canvas);
//        mEraserBitmap.eraseColor(mMaskColor);
        mEraserCanvas.drawColor(getColor());
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


    /**
     * Recycle mask drawable.
     *
     * @return the mask drawable
     */
    public MaskDrawable recycle() {
        if (mEraserBitmap != null && !mEraserBitmap.isRecycled()) {
            mEraserBitmap.recycle();
        }
        return this;
    }

    /**
     * Is recycled boolean.
     *  是否已释放
     * @return the boolean
     */
    public boolean isRecycled() {
        return mEraserBitmap == null || mEraserBitmap.isRecycled();
    }


    private int trant(float i) {
        return (int)i;
    }

    /**
     * The type Builder.
     */
    public static final class Builder {
        private int mBackgroundColor = DEFAULT_MASK_COLOR;
        private int mMaskColor = DEFAULT_MASK_COLOR;
        private int width;
        private int height;
        private float mLeft;
        private float mTop;
        private float mRight;
        private float mBottom;
        private float mRadius;
        private float mRx;
        private float mRy;
        private MaskType mMaskType = MaskType.CIRCLE;

        private Builder() {}

        private Builder(int width,int height) {
            this.width = width;
            this.height = height;
        }

        public Builder circle(float radiu,float rx,float ry) {
            mMaskType = CIRCLE;
            this.mRadius = radiu;
            this.mRx = rx;
            this.mRy = ry;
            return this;
        }

        public Builder circle(float radiu) {
            mMaskType = CIRCLE;
            this.mRadius = mRx = mRy = radiu;
            return this;
        }

        public Builder backgroundColor(int color) {
            mBackgroundColor = color;
            return this;
        }


        public Builder maskColor(int color) {
            mMaskColor = color;
            return this;
        }

        public Builder rect(float left,float top,float right,float bottom) {
            mMaskType = RECT;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            return this;
        }

        public Builder oval(float left,float top,float right,float bottom) {
            mMaskType = OVAL;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            return this;
        }

        public Builder roundRect(float left,float top,float right,float bottom,float rx,float ry) {
            mMaskType = ROUND_RECT;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            this.mRx = rx;
            this.mRy = ry;
            return this;
        }

        public Builder roundRect(float left,float top,float right,float bottom,float radius) {
            mMaskType = ROUND_RECT;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            this.mRadius = radius;
            return this;
        }
        /**
         * Build mask drawable.
         *
         * @return the mask drawable
         */
        public MaskDrawable build() {
            return new MaskDrawable(this);
        }
    }
}
