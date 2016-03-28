package com.neilmarietta.hipchops.interactor;

import com.neilmarietta.hipchops.data.InputOutput;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.util.MessageParser;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetInputMessageListUseCase {

    private MessageParser mMessageParser;

    public GetInputMessageListUseCase(MessageParser messageParser) {
        mMessageParser = messageParser;
    }

    @SuppressWarnings("unchecked")
    public void execute(Subscriber subscriber) {
        buildObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Observable buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<IOMessage>>() {
            @Override
            public void call(final Subscriber<? super List<IOMessage>> subscriber) {
                final List<IOMessage> messages = new ArrayList<>();

                for (final InputOutput current : TestCases.sTestArray) {
                    new ParseMessageUseCase(mMessageParser).setMessage(current.in).execute(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            if (messages.size() == TestCases.sTestArray.length)
                                subscriber.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onNext(String result) {
                            messages.add(new IOMessage(current.in, result));
                            subscriber.onNext(messages);
                        }
                    });
                }
            }
        });
    }
}
