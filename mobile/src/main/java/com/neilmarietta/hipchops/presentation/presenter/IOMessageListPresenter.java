package com.neilmarietta.hipchops.presentation.presenter;

import android.support.annotation.NonNull;

import com.neilmarietta.hipchops.contract.IOMessageList;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.interactor.MessageUseCase;
import com.neilmarietta.hipchops.presentation.BasePresenter;
import com.neilmarietta.hipchops.presentation.model.IOMessage;

import javax.inject.Inject;

import rx.Subscriber;

public class IOMessageListPresenter extends BasePresenter<IOMessageList.View>
        implements IOMessageList.UserActionListener {

    private MessageUseCase mMessageUseCase;

    @Inject
    public IOMessageListPresenter(MessageUseCase messageUseCase) {
        mMessageUseCase = messageUseCase;
    }

    @Override
    public void attachView(@NonNull IOMessageList.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
        mMessageUseCase.unsubscribe();
    }

    public void initialize() {
        loadMessageList();
    }

    @Override
    public void loadMessageList() {
        getMvpView().showLoading();
        getMvpView().renderMessageList(null);
        getMessageList();
    }

    @Override
    public void addMessage() {
        getMvpView().showLoading();
        getMessage();
    }

    @Override
    public void addMessage(@NonNull String input) {
        getMvpView().showLoading();
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
            IOMessageListPresenter.this.getMvpView().hideLoading();
        }

        @Override
        public void onError(Throwable e) {
            IOMessageListPresenter.this.getMvpView().hideLoading();
            IOMessageListPresenter.this.getMvpView().showError(e.getMessage());
        }

        @Override
        public void onNext(IOMessage message) {
            IOMessageListPresenter.this.getMvpView().addMessage(message);
        }
    }
}
