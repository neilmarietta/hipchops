package com.neilmarietta.hipchops.interactor;

import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.util.MessageParser;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

public class ParseMessageUseCase extends UseCase {

    private final MessageParser mMessageParser;

    private final String mMessage;

    public ParseMessageUseCase(String message, MessageParser messageParser) {
        mMessageParser = messageParser;
        mMessage = message;
    }

    public Observable buildObservable() {
        return Observable.create(new rx.Observable.OnSubscribe<Message>() {
            @Override
            public void call(Subscriber<? super Message> subscriber) {
                try {
                    subscriber.onNext(mMessageParser.parse(mMessage));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
