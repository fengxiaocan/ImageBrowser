package com.app.baselib.indicator;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * A data structure that holds a Shape and various properties that can be
 * used to define
 * how the shape is drawn.
 */
class ShapeHolder {
    private float x = 0, y = 0;//
    private ShapeDrawable shape;
    private int           color;
    private float alpha = 1f;
    private Paint paint;

    /**
     * Sets paint.
     *
     * @param value
     *         the value
     */
    public void setPaint(Paint value) {
        paint = value;
    }

    /**
     * Gets paint.
     *
     * @return the paint
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * Sets x.
     *
     * @param value
     *         the value
     */
    public void setX(float value) {
        x = value;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * Sets y.
     *
     * @param value
     *         the value
     */
    public void setY(float value) {
        y = value;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets shape.
     *
     * @param value
     *         the value
     */
    public void setShape(ShapeDrawable value) {
        shape = value;
    }

    /**
     * Gets shape.
     *
     * @return the shape
     */
    public ShapeDrawable getShape() {
        return shape;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param value
     *         the value
     */
    public void setColor(int value) {
        shape.getPaint().setColor(value);
        color = value;

    }

    /**
     * Sets alpha.
     *
     * @param alpha
     *         the alpha
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        shape.setAlpha((int) ((alpha * 255f) + .5f));
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public float getWidth() {
        return shape.getShape().getWidth();
    }

    /**
     * Sets width.
     *
     * @param width
     *         the width
     */
    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public float getHeight() {
        return shape.getShape().getHeight();
    }

    /**
     * Sets height.
     *
     * @param height
     *         the height
     */
    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }

    /**
     * Resize shape.
     *
     * @param width
     *         the width
     * @param height
     *         the height
     */
    public void resizeShape(final float width,final float height){
        shape.getShape().resize(width,height);
    }

    /**
     * Instantiates a new Shape holder.
     *
     * @param s
     *         the s
     */
    public ShapeHolder(ShapeDrawable s) {
        shape = s;
    }
}
