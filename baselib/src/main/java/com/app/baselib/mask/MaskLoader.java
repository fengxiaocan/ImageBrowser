package com.app.baselib.mask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import static com.app.baselib.mask.MaskType.CIRCLE;
import static com.app.baselib.mask.MaskType.OVAL;
import static com.app.baselib.mask.MaskType.RECT;
import static com.app.baselib.mask.MaskType.ROUND_RECT;

/**
 * The type Msdk loader.
 */
public class MaskLoader {
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
    protected int mMaskColor;
    /**
     * The M background color.
     */
    protected int mBackgroundColor;

    /**
     * The Width.
     */
    protected int width;

    /**
     * The Height.
     */
    protected int height;

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
    protected MaskType mMaskType;//默认为圆形的类型


    private MaskLoader(Builder builder) {
        mMaskColor = builder.mMaskColor;
        mBackgroundColor = builder.mBackgroundColor;
        width = builder.width;
        height = builder.height;
        mLeft = builder.mLeft;
        mTop = builder.mTop;
        mRight = builder.mRight;
        mBottom = builder.mBottom;
        mRadius = builder.mRadius;
        mRx = builder.mRx;
        mRy = builder.mRy;
        mMaskType = builder.mMaskType;
        init();
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
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder(View view) {
        return new Builder(view.getMeasuredWidth(),view.getMeasuredHeight());
    }

    public static void recycle() {
        MaskEngine.getInstance().recycle();
    }

    /**
     * Init.
     */
    protected void init() {
        if (mMaskType == MaskType.CIRCLE) {
            if (mRadius < 0) {
                mRadius = mRx = mRy = Math.min(width,height) / 2;
            }
        }

        mEraserBitmap = MaskEngine.getInstance().getBitmap(mMaskColor);
        mEraserCanvas = new Canvas(mEraserBitmap);
        mEraser = new Paint();
        mEraser.setAntiAlias(true);
        mEraser.setColor(mMaskColor);
        mEraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mEraser.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * Draw.
     *
     * @param canvas the canvas
     */
    public void draw(Canvas canvas) {
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


    /**
     * The type Builder.
     */
    public static final class Builder {
        private int mMaskColor = DEFAULT_MASK_COLOR;
        private int mBackgroundColor = DEFAULT_MASK_BRACKGROUND;
        private int width;
        private int height;
        private float mLeft = 0;
        private float mTop = 0;
        private float mRight;
        private float mBottom;
        private float mRadius;
        private float mRx;
        private float mRy;
        private MaskType mMaskType = CIRCLE;

        private Builder(int width,int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Circle builder.
         *
         * @param radiu the radiu
         * @param rx the rx
         * @param ry the ry
         * @return the builder
         */
        public Builder circle(float radiu,float rx,float ry) {
            mMaskType = CIRCLE;
            this.mRadius = radiu;
            this.mRx = rx;
            this.mRy = ry;
            return this;
        }


        /**
         * Mask color builder.
         *
         * @param color the color
         * @return the builder
         */
        public Builder maskColor(int color) {
            mMaskColor = color;
            return this;
        }

        /**
         * Circle builder.
         *
         * @param radiu the radiu
         * @return the builder
         */
        public Builder circle(float radiu) {
            mMaskType = CIRCLE;
            this.mRadius = mRx = mRy = radiu;
            return this;
        }

        /**
         * Background color builder.
         *
         * @param color the color
         * @return the builder
         */
        public Builder backgroundColor(int color) {
            mBackgroundColor = color;
            return this;
        }

        /**
         * Rect builder.
         *
         * @param left the left
         * @param top the top
         * @param right the right
         * @param bottom the bottom
         * @return the builder
         */
        public Builder rect(float left,float top,float right,float bottom) {
            mMaskType = RECT;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            return this;
        }

        /**
         * Oval builder.
         *
         * @param left the left
         * @param top the top
         * @param right the right
         * @param bottom the bottom
         * @return the builder
         */
        public Builder oval(float left,float top,float right,float bottom) {
            mMaskType = OVAL;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            return this;
        }

        /**
         * Round rect builder.
         *
         * @param left the left
         * @param top the top
         * @param right the right
         * @param bottom the bottom
         * @param rx the rx
         * @param ry the ry
         * @return the builder
         */
        public Builder roundRect(float left,float top,float right,float bottom,float rx,float ry) {
            this.mMaskType = ROUND_RECT;
            this.mLeft = left;
            this.mRight = right;
            this.mTop = top;
            this.mBottom = bottom;
            this.mRx = rx;
            this.mRy = ry;
            return this;
        }

        /**
         * Round rect builder.
         *
         * @return the builder
         */
        public Builder roundRectMatchView(View view,float radius) {
            this.mMaskType = ROUND_RECT;
            this.mLeft = 0;
            this.mRight = view.getMeasuredWidth();
            this.mTop = 0;
            this.mBottom = view.getMeasuredHeight();
            this.mRx = radius;
            this.mRy = radius;
            return this;
        }

        /**
         * Round rect builder.
         *
         * @param left the left
         * @param top the top
         * @param right the right
         * @param bottom the bottom
         * @param radius the radius
         * @return the builder
         */
        public Builder roundRect(float left,float top,float right,float bottom,float radius) {
            mMaskType = ROUND_RECT;
            mLeft = left;
            mRight = right;
            mTop = top;
            mBottom = bottom;
            this.mRadius = this.mRx = this.mRy = radius;
            return this;
        }

        /**
         * Build msdk loader.
         *
         * @return the msdk loader
         */
        public MaskLoader build() {
            return new MaskLoader(this);
        }
    }
}
