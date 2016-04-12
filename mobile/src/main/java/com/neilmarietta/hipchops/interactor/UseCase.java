package com.neilmarietta.hipchops.interactor;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class UseCase {

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private rx.Scheduler mSubscribeOn = Schedulers.newThread();
    private rx.Scheduler mObserveOn = AndroidSchedulers.mainThread();

    @SuppressWarnings("unchecked")
    public final void execute(Observable observable, Subscriber subscriber) {
        mCompositeSubscription
                .add(observable
                        .subscribeOn(mSubscribeOn)
                        .observeOn(mObserveOn)
                        .subscribe(subscriber));
    }

    public void setSubscribeOn(rx.Scheduler scheduler) {
        mSubscribeOn = scheduler;
    }

    public void setObserveOn(rx.Scheduler scheduler) {
        mObserveOn = scheduler;
    }

    public void unsubscribe() {
        mCompositeSubscription.clear();
    }
}
