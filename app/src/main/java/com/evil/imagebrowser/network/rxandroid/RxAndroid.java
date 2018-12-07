package com.evil.imagebrowser.network.rxandroid;

import com.app.baselib.log.LogUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/9/18
 * @desc rxandroid 封装一下,方便以后统一修改因为版本更新的问题而导致的大换血,第三方框架老这么干的
 */
public class RxAndroid<T> {
	Observable<T> obser;
	
	protected RxAndroid(Observable<T> obser) {
		this.obser = obser;
	}
	
	public static <T> RxAndroid<T> create(ObservableOnSubscribe<T> source) {
		return new RxAndroid<>(Observable.create(source));
	}
	
	public static <T> RxAndroid<T> just(T t) {
		return new RxAndroid<>(Observable.just(t));
	}
	
	public static <T> RxAndroid<T> fromArray(T... t) {
		return new RxAndroid<>(Observable.fromArray(t));
	}
	
	public static <T> RxAndroid<T> fromCallable(Callable<? extends T> supplier) {
		return new RxAndroid<>(Observable.fromCallable(supplier));
	}
	
	public static <T> RxAndroid<T> fromFuture(Future<? extends T> future) {
		return new RxAndroid<>(Observable.fromFuture(future));
	}
	
	public static <T> RxAndroid<T> createOnThread(ObservableOnSubscribe<T> source) {
		return new RxAndroid<>(Observable.create(source)).subscribeOnThread().observeOnMain();
	}
	
	/**
	 * 在新的线程中调度
	 *
	 * @return
	 */
	public RxAndroid<T> subscribeOnThread() {
		obser = obser.subscribeOn(Schedulers.newThread());
		return this;
	}
	
	public RxAndroid<T> subscribeOn(Scheduler scheduler) {
		obser = obser.subscribeOn(scheduler);
		return this;
	}
	
	public RxAndroid<T> observeOn(Scheduler scheduler) {
		obser = obser.observeOn(scheduler);
		return this;
	}
	
	/**
	 * 在主线程中接收
	 *
	 * @return
	 */
	public RxAndroid<T> observeOnMain() {
		obser = obser.observeOn(AndroidSchedulers.mainThread());
		return this;
	}
	
	/**
	 * 最多保留的事件数。
	 *
	 * @param count
	 * @return
	 */
	public RxAndroid<T> take(long count) {
		obser = obser.take(count);
		return this;
	}
	
	/**
	 * 条件过滤，去除不符合某些条件的事件。
	 *
	 * @param predicate
	 * @return
	 */
	public RxAndroid<T> filter(Predicate<? super T> predicate) {
		obser = obser.filter(predicate);
		return this;
	}
	
	/**
	 * io线程
	 *
	 * @return
	 */
	public RxAndroid<T> io() {
		obser = obser.subscribeOn(Schedulers.io());
		return this;
	}
	
	public Disposable subscribe(Consumer<? super T> onNext) {
		return obser.subscribe(onNext);
	}
	
	/**
	 * Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件. 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
	 *
	 * @param other
	 * @param zipper
	 * @param <U>
	 * @param <R>
	 * @return
	 */
	public <U,R> Observable<R> zipWith(
			Iterable<U> other,BiFunction<? super T,? super U,? extends R> zipper)
	{
		return obser.zipWith(other,zipper);
	}
	
	/**
	 * 通过Map, 可以将Observable发来的事件转换为任意的类型
	 *
	 * @param mapper
	 * @param <R>
	 * @return
	 */
	public <R> Observable<R> map(Function<? super T,? extends R> mapper) {
		return obser.map(mapper);
	}
	
	public Disposable subscribeWithError(Consumer<? super T> onNext) {
		return obser.subscribe(onNext,new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				if (throwable != null) { LogUtils.e("RxAndroid",throwable.getMessage()); }
			}
		});
	}
	
	public Disposable subscribe() {
		return obser.subscribe();
	}
	
	public Disposable subscribeNoError() {
		return obser.subscribe(Functions.emptyConsumer(),new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				try {
					LogUtils.e("noah","RxAndroid subscribeNoError " + throwable.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void subscribe(Observer<? super T> observer) {
		obser.subscribe(observer);
	}
	
	public RxAndroid<T> subscribeOnMain() {
		obser = obser.subscribeOn(AndroidSchedulers.mainThread());
		return this;
	}
	
	public RxAndroid<T> delay(long delay) {
		obser = obser.delay(delay,TimeUnit.MILLISECONDS);
		return this;
	}
}
