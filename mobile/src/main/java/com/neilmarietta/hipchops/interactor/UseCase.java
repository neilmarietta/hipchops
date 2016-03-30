package com.neilmarietta.hipchops.interactor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class UseCase {

    private Subscription mSubscription = Subscriptions.empty();

    private rx.Scheduler mSubscribeOn = Schedulers.newThread();
    private rx.Scheduler mObserveOn = AndroidSchedulers.mainThread();

    @SuppressWarnings("unchecked")
    public final void execute(Subscriber subscriber) {
        mSubscription = buildObservable()
                .subscribeOn(mSubscribeOn)
                .observeOn(mObserveOn)
                .subscribe(subscriber);
    }

    public void setSubscribeOn(rx.Scheduler scheduler) {
        mSubscribeOn = scheduler;
    }

    public void setObserveOn(rx.Scheduler scheduler) {
        mObserveOn = scheduler;
    }

    protected abstract Observable buildObservable();

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
