package com.app.baselib.helper;

/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.helper
 * @author: Noah.冯 QQ:1066537317
 * @time: 16:00
 * @version: 1.1
 * @desc： TODO
 */

public class ThreadTask{

    private Runnable mRunnable;
    private long     id;

    public void setRunnable(Runnable runnable){
        mRunnable = runnable;
    }

    public void setId(long id){
        this.id = id;
    }

    public ThreadTask(Runnable runnable){
        mRunnable = runnable;
        this.id = System.currentTimeMillis();
    }
    public ThreadTask(Runnable runnable,long id){
        mRunnable = runnable;
        this.id = id;
    }

    public Runnable getRunnable(){
        return mRunnable;
    }

    public long getId(){
        return id;
    }

    @Override
    public boolean equals(Object obj){
        return this.id == ((ThreadTask) obj).id;
    }
}
