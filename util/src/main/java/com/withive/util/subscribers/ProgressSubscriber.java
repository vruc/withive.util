package com.withive.util.subscribers;


import android.content.Context;

import com.orhanobut.logger.Logger;
import com.withive.util.progress.ProgressBarHandler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 进度观察者
 */
public class ProgressSubscriber<T> implements Observer<T> {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context mContext;
    private ProgressBarHandler mProgressBarHandler;

    /**
     *
     */
    public static boolean delayHideProgressBar = true;

    /**
     * 进度条的订阅，居中显示进度
     *
     * @param mSubscriberOnNextListener 监听者
     * @param mContext                  上下文对象
     */
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context mContext) {
        this(mSubscriberOnNextListener, mContext, false);
    }

    /**
     * 进度条的订阅
     *
     * @param mSubscriberOnNextListener 监听者
     * @param mContext                  上下文对象
     * @param styleHorizontal           样式
     */
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context mContext, boolean styleHorizontal) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = mContext;

        int defStyle = styleHorizontal ? android.R.attr.progressBarStyleHorizontal : android.R.attr.progressBarStyle;

        mProgressBarHandler = new ProgressBarHandler(this.mContext, defStyle, delayHideProgressBar);
    }

    @Override
    public void onSubscribe(Disposable d) {
        Logger.w("ProgressSubscriber onSubscribe");
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onSubscriber();
        }
        if (mProgressBarHandler != null) {
            mProgressBarHandler.obtainMessage(ProgressBarHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onNext(T t) {
//        Logger.w("ProgressSubscriber onNext %s", t.getClass().getSimpleName());
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.w("ProgressSubscriber onError %s", e.getMessage());
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError(e);
        }
        if (mProgressBarHandler != null) {
            mProgressBarHandler.obtainMessage(ProgressBarHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onComplete() {
        Logger.w("ProgressSubscriber onComplete");
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onCompleted();
        }
        if (mProgressBarHandler != null) {
            mProgressBarHandler.obtainMessage(ProgressBarHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }
}
