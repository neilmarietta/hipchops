package com.neilmarietta.hipchops.presentation.presenter;

import android.support.annotation.NonNull;

import com.neilmarietta.hipchops.contract.IOMessageListContract;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.interactor.MessageUseCase;
import com.neilmarietta.hipchops.presentation.model.IOMessage;

import javax.inject.Inject;

import rx.Subscriber;

public class IOMessageListPresenter implements Presenter, IOMessageListContract.UserActionListener {

    private MessageUseCase mMessageUseCase;

    private IOMessageListContract.View mMessageListView;

    @Inject
    public IOMessageListPresenter(MessageUseCase messageUseCase) {
        mMessageUseCase = messageUseCase;
    }

    public void setView(@NonNull IOMessageListContract.View view) {
        mMessageListView = view;
    }

    public void initialize() {
        loadMessageList();
    }

    @Override
    public void loadMessageList() {
        showViewLoading();
        mMessageListView.renderMessageList(null);
        getMessageList();
    }

    @Override
    public void addMessage() {
        showViewLoading();
        getMessage();
    }

    @Override
    public void addMessage(@NonNull String input) {
        showViewLoading();
        getMessage(input);
    }

    private void getMessageList() {
        mMessageUseCase.getMessageList(TestCases.getInputs(), new IOMessageListSubscriber());
    }

    private void getMessage() {
        getMessage(TestCases.getNextInput());
    }

    private void getMessage(String input) {
        mMessageUseCase.getMessage(input, new IOMessageListSubscriber());
    }

    private final class IOMessageListSubscriber extends Subscriber<IOMessage> {

        @Override
        public void onCompleted() {
            IOMessageListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            IOMessageListPresenter.this.hideViewLoading();
            IOMessageListPresenter.this.showErrorMessage(e.getMessage());
        }

        @Override
        public void onNext(IOMessage message) {
            IOMessageListPresenter.this.mMessageListView.addMessage(message);
        }
    }

    private void showViewLoading() {
        mMessageListView.showLoading();
    }

    private void hideViewLoading() {
        mMessageListView.hideLoading();
    }

    private void showErrorMessage(String errorMessage) {
        mMessageListView.showError(errorMessage);
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mMessageListView = null;
        mMessageUseCase.unsubscribe();
    }
}
