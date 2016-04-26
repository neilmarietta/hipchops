package com.neilmarietta.hipchops.presentation;

public interface MvpLoadView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String message);
}
