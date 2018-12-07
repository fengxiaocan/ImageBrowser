package com.app.baselib.intface;

/**
 *  @项目名： BaseApp
 *  @包名： com.dgtle.baselib.intface
 *  @创建者: Noah.冯
 *  @时间: 16:45
 *  @描述： 加载数据需要的接口
 */
public interface ILoadDataView<T> {
    /**
     * 第一次加载数据
     */
    void loadFirst(T t);
    /**
     * 加载更多数据
     */
    void loadMore(T t, int num);
    /**
     * 加载开始
     */
    void startLoad(T t);
    /**
     * 正在加载中
     */
    void loading(T t);

    /**
     * 加载成功
     */
    void loadSuccess(T t);
    /**
     * 加载失败
     */
    void loadFailure(T t);
}
