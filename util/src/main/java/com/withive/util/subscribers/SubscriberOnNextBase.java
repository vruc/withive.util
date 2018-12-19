package com.withive.util.subscribers;

import com.orhanobut.logger.Logger;

public abstract class SubscriberOnNextBase<T> implements SubscriberOnNextListener<T> {
    @Override
    public void onSubscriber() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Logger.e(e, "SubscriberOnNextBase onError");
    }
}
