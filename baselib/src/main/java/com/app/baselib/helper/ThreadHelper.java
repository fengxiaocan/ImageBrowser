package com.app.baselib.helper;

import java.util.LinkedList;

/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.util
 * @author: Noah.冯 QQ:1066537317
 * @time: 20:38
 * @version: 1.1
 * @desc： TODO
 */

public final class ThreadHelper{

    private static final ThreadHelper THREAD_HELPER = new ThreadHelper();

    private Thread mThread;
    private boolean isRun = true;
    private LinkedList<ThreadTask> mTasks;

    private ThreadHelper(){
    }

    public static ThreadHelper create(){
        if(THREAD_HELPER.mThread == null){
            THREAD_HELPER.mThread = new Thread(THREAD_HELPER.mRunnable);
        }
        if(THREAD_HELPER.mTasks == null){
            THREAD_HELPER.mTasks = new LinkedList<>();
        }
        return THREAD_HELPER;
    }

    public ThreadHelper addTask(ThreadTask task){
        if(mTasks == null){
            mTasks = new LinkedList<>();
        }
        mTasks.add(task);
        return THREAD_HELPER;
    }

    public ThreadHelper run(){
        isRun = true;
        mThread.start();
        return THREAD_HELPER;
    }

    public ThreadHelper addFirstTask(ThreadTask task){
        if(mTasks == null){
            mTasks = new LinkedList<>();
        }
        mTasks.addFirst(task);
        return THREAD_HELPER;
    }

    public ThreadHelper removeFirstTask(){
        if(mTasks != null){
            mTasks.removeFirst();
        }
        return THREAD_HELPER;
    }

    public ThreadHelper removeTask(ThreadTask task){
        if(mTasks != null){
            mTasks.remove(task);
        }
        return THREAD_HELPER;
    }


    public ThreadHelper removeLast(){
        if(mTasks != null){
            mTasks.removeLast();
        }
        return THREAD_HELPER;
    }

    private Runnable mRunnable = new Runnable(){
        @Override
        public void run(){
            while(isRun && mTasks != null && mTasks.size() > 0){
                ThreadTask task = mTasks.getFirst();
                mTasks.removeFirst();
                Runnable runnable = task.getRunnable();
                runnable.run();
            }
        }
    };

    public ThreadHelper stop(){
        isRun = false;
        return THREAD_HELPER;
    }
}
