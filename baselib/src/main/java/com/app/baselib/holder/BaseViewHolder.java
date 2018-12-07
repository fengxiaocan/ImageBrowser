package com.app.baselib.holder;

/**
 * The type Base view holder.
 *
 * @param <T>
 *         the type parameter
 *
 * @创建者: Noah.冯
 * @时间: 11 :30
 * @描述： TODO
 */
public abstract class BaseViewHolder<T> {

    /**
     * 初始化数据
     *
     * @param position
     *         the position
     * @param t
     *         the t
     */
    public abstract void initData(int position, T t);
}
