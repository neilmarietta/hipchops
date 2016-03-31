package com.neilmarietta.hipchops.presentation.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.interactor.GetInputMessageListUseCase;
import com.neilmarietta.hipchops.interactor.ParseMessageUseCase;
import com.neilmarietta.hipchops.internal.di.component.DaggerMessageComponent;
import com.neilmarietta.hipchops.internal.di.module.MessageModule;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.presentation.view.IOMessageListView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class IOMessageListPresenter implements Presenter {

    private static Gson sGson = new Gson();

    private GetInputMessageListUseCase mGetInputMessageListUseCase;

    private IOMessageListView mMessageListView;

    @Inject
    public IOMessageListPresenter(GetInputMessageListUseCase inputMessageListUseCase) {
        mGetInputMessageListUseCase = inputMessageListUseCase;
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

    private ParseMessageUseCase getParseMessageUseCase(String input) {
        return DaggerMessageComponent.builder()
                .messageModule(new MessageModule(input))
                .build().parseMessageUseCase();
    }

    private void getMessage() {
        String input = TestCases.getNextInput();
        getParseMessageUseCase(input).execute(new ParseOutputSubscriber(input));
    }

    private void getMessage(String input) {
        getParseMessageUseCase(input).execute(new ParseOutputSubscriber(input));
    }

    private final class ParseOutputSubscriber extends Subscriber<Message> {

        private final String mInput;

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
        public void onNext(Message result) {
            // TODO : Do not do toJson on mainThread
            String output = sGson.toJson(result);

            IOMessageListPresenter.this.mMessageListView.addMessage(new IOMessage(mInput, output, result));
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
