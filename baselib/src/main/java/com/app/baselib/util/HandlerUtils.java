package com.app.baselib.util;

import android.os.Handler;
import android.os.Message;

import com.app.baselib.BaseApplication;
import com.app.baselib.intface.IHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *
 *     time  : 16/11/01
 *     desc  : Handler相关工具类
 * </pre>
 */
public final class HandlerUtils{

    private HandlerUtils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Handler       sHandler;
    private static List<IHandler> sMessageList = new ArrayList<>();

    /**
     * Init handler.
     *
     * @param handler
     *         the handler
     */
    public static void initHandler(Handler handler){
        sHandler = handler;
    }

    /**
     * Send message.
     *
     * @param message
     *         the message
     */
    public static void sendMessage(Message message){
        getHandler().sendMessage(message);
    }

    /**
     * Send empty message.
     *
     * @param what
     *         the what
     */
    public static void sendEmptyMessage(int what){
        getHandler().sendEmptyMessage(what);
    }

    /**
     * Send empty message delayed.
     *
     * @param what
     *         the what
     * @param delayMillis
     *         the delay millis
     */
    public static void sendEmptyMessageDelayed(int what,long delayMillis){
        getHandler().sendEmptyMessageDelayed(what,delayMillis);
    }

    /**
     * Send empty message at time.
     *
     * @param what
     *         the what
     * @param delayMillis
     *         the delay millis
     */
    public static void sendEmptyMessageAtTime(int what,long delayMillis){
        getHandler().sendEmptyMessageAtTime(what,delayMillis);
    }

    /**
     * Send message delayed.
     *
     * @param msg
     *         the msg
     * @param delayMillis
     *         the delay millis
     */
    public static void sendMessageDelayed(Message msg,long delayMillis){
        getHandler().sendMessageDelayed(msg,delayMillis);
    }

    /**
     * Send message at time.
     *
     * @param msg
     *         the msg
     * @param delayMillis
     *         the delay millis
     */
    public static void sendMessageAtTime(Message msg,long delayMillis){
        getHandler().sendMessageAtTime(msg,delayMillis);
    }

    /**
     * Post.
     *
     * @param runnable
     *         the runnable
     */
    public static void post(Runnable runnable){
        getHandler().post(runnable);
    }

    /**
     * Post delayed.
     *
     * @param runnable
     *         the runnable
     * @param delayMillis
     *         the delay millis
     */
    public static void postDelayed(Runnable runnable,long delayMillis){
        getHandler().postDelayed(runnable,delayMillis);
    }

    /**
     * Post at time.
     *
     * @param runnable
     *         the runnable
     * @param delayMillis
     *         the delay millis
     */
    public static void postAtTime(Runnable runnable,long delayMillis){
        getHandler().postAtTime(runnable,delayMillis);
    }

    /**
     * Has messages boolean.
     *
     * @param what
     *         the what
     *
     * @return the boolean
     */
    public static boolean hasMessages(int what){
        return getHandler().hasMessages(what);
    }

    /**
     * Obtain message message.
     *
     * @return the message
     */
    public static Message obtainMessage(){
        return getHandler().obtainMessage();
    }

    /**
     * Obtain message message.
     *
     * @param what
     *         the what
     *
     * @return the message
     */
    public static Message obtainMessage(int what){
        return getHandler().obtainMessage(what);
    }

    /**
     * Get handler handler.
     *
     * @return the handler
     */
    public static Handler getHandler(){
        if(sHandler == null){
            sHandler = new BaseApplication.MyHandler(Utils.getContext().getMainLooper());
        }
        return sHandler;
    }

    /**
     * Get message list list.
     *
     * @return the list
     */
    public static List<IHandler> getMessageList(){
        return sMessageList;
    }

    /**
     * Register handler.
     *
     * @param message
     *         the message
     */
    public static void registerHandler(IHandler message){
        sMessageList.add(message);
    }

    /**
     * Unregister handler.
     *
     * @param message
     *         the message
     */
    public static void unregisterHandler(IHandler message){
        sMessageList.remove(message);
    }

    /**
     * Clear handler.
     */
    public static void clearHandler(){
        sMessageList.clear();
    }

    /**
     * Remove callbacks.
     *
     * @param runnable
     *         the runnable
     */
    public static void removeCallbacks(Runnable runnable){
        sHandler.removeCallbacks(runnable);
    }

    /**
     * Remove callbacks.
     *
     * @param r
     *         the r
     * @param token
     *         the token
     */
    public static void removeCallbacks(Runnable r,Object token){
        sHandler.removeCallbacks(r,token);
    }

    /**
     * Remove messages.
     *
     * @param what
     *         the what
     */
    public static void removeMessages(int what){
        sHandler.removeMessages(what);
    }

    /**
     * Remove messages.
     *
     * @param what
     *         the what
     * @param object
     *         the object
     */
    public static void removeMessages(int what,Object object){
        sHandler.removeMessages(what,object);
    }


    /**
     * The type Handler holder.
     */
    public static class HandlerHolder extends Handler{

        /**
         * The M listener weak reference.
         */
        WeakReference<Callback> mListenerWeakReference;

        /**
         * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
         *
         * @param listener
         *         收到消息回调接口
         */
        public HandlerHolder(Callback listener){
            mListenerWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg){
            if(mListenerWeakReference != null && mListenerWeakReference
                    .get() != null)
            {
                mListenerWeakReference.get().handleMessage(msg);
            }
        }
    }
}
