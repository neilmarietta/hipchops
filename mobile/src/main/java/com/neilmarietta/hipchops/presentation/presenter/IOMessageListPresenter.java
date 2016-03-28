package com.neilmarietta.hipchops.presentation.presenter;

import android.support.annotation.NonNull;

import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.interactor.GetInputMessageListUseCase;
import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.presentation.view.IOMessageListView;

import java.util.List;

import rx.Subscriber;

public class IOMessageListPresenter implements Presenter {

    private ParseMessageUseCase mParseMessageUseCase;
    private GetInputMessageListUseCase mGetInputMessageListUseCase;

    private IOMessageListView mMessageListView;

    public IOMessageListPresenter(GetInputMessageListUseCase inputMessageListUseCase,
                                  ParseMessageUseCase parseMessageUseCase) {
        mGetInputMessageListUseCase = inputMessageListUseCase;
        mParseMessageUseCase = parseMessageUseCase;
    }

    public void setView(@NonNull IOMessageListView view) {
        mMessageListView = view;
    }

    public void initialize() {
        loadMessageList();
    }

    public void loadMessageList() {
        showViewLoading();
        getMessageList();
    }

    public void addMessage() {
        showViewLoading();
        getMessage();
    }

    public void addMessage(String input) {
        showViewLoading();
        getMessage(input);
    }

    private void getMessageList() {
        mGetInputMessageListUseCase.execute(new IOMessageListSubscriber());
    }

    private final class IOMessageListSubscriber extends Subscriber<List<IOMessage>> {

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
        public void onNext(List<IOMessage> messageList) {
            IOMessageListPresenter.this.mMessageListView.renderMessageList(messageList);
        }
    }

    private void getMessage() {
        String input = TestCases.getNextInput();
        mParseMessageUseCase.setMessage(input).execute(new ParseOutputSubscriber(input));
    }

    private void getMessage(String input) {
        mParseMessageUseCase.setMessage(input).execute(new ParseOutputSubscriber(input));
    }

    private final class ParseOutputSubscriber extends Subscriber<String> {

        private String mInput;

        public ParseOutputSubscriber(String input) {
            mInput = input;
        }

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
        public void onNext(String output) {
            IOMessageListPresenter.this.mMessageListView.addMessage(new IOMessage(mInput, output));
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
    }
}
