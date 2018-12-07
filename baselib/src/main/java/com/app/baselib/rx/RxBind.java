package com.app.baselib.rx;


import android.view.View;

import java.util.concurrent.ExecutorService;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc 线程调度器
 */
public class RxBind<T,V extends View> {
    private ExecutorService pool;
    private RxConfigs mRxConfigs;
    private RxBinding<T,V> subscriber;
    private RxBindAcceptor<V,T> rxAcceptor;

    RxBind(ExecutorService pool,RxConfigs rxScheduler) {
        this.pool = pool;
        mRxConfigs = rxScheduler;
    }

    RxBind<T,V> setSubscriber(RxBinding<T,V> subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public RxBind<T,V> bind(RxBindAcceptor<V,T> rxAcceptor) {
        this.rxAcceptor = rxAcceptor;
        return this;
    }

    public void into(V view) {
        RxBindRunnable rxBindRunnable = new RxBindRunnable();
        rxBindRunnable.acceptor(rxAcceptor);
        rxBindRunnable.subscriber(subscriber);
        rxBindRunnable.into(view);
        Runnable mRunnable = new RxRunnable(mRxConfigs).runnable(rxBindRunnable);
        DelayTaskDispatcher.get().postDelay(mRxConfigs.delay,pool,mRunnable);
    }

    static class RxBindRunnable<V extends View,T> implements Runnable {
        private RxBindAcceptor<V,T> mAcceptor;
        private RxBinding<T,V> subscriber;
        private V view;

        public RxBindRunnable<V,T> acceptor(RxBindAcceptor<V,T> acceptor) {
            mAcceptor = acceptor;
            return this;
        }

        public RxBindRunnable<V,T> subscriber(RxBinding<T,V> subscriber) {
            this.subscriber = subscriber;
            return this;
        }

        public RxBindRunnable<V,T> into(V view) {
            this.view = view;
            return this;
        }

        @Override
        public void run() {
            subscriber.onSubscribe(new RxBindEmitter<T>() {
                @Override
                public void onStart(final T data) {
                    RxExecutor.main().execute(new Runnable() {
                        @Override
                        public void run() {
                            mAcceptor.onStart(view,data);
                        }
                    });
                }

                @Override
                public void onComplete(final T data) {
                    RxExecutor.main().execute(new Runnable() {
                        @Override
                        public void run() {
                            mAcceptor.onComplete(view,data);
                        }
                    });
                }

                @Override
                public void onError(final T data) {
                    RxExecutor.main().execute(new Runnable() {
                        @Override
                        public void run() {
                            mAcceptor.onError(view,data);
                        }
                    });
                }
            });
        }
    }
}
