package com.neilmarietta.hipchops.presentation;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();
}