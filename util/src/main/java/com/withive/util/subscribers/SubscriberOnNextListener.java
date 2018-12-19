package com.withive.util.subscribers;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onSubscriber();
    void onCompleted();
    void onError(Throwable e);
}
