package com.neilmarietta.hipchops.interactor;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.util.MessageParser;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ParseMessageUseCase {

    private static Gson sGson = new Gson();

    private MessageParser mMessageParser;

    private String mMessage;

    public ParseMessageUseCase(MessageParser messageParser) {
        mMessageParser = messageParser;
    }

    public ParseMessageUseCase setMessage(String message) {
        mMessage = message;
        return this;
    }

    @SuppressWarnings("unchecked")
    public void execute(Subscriber subscriber) {
        buildObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Observable buildObservable() {
        return Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(sGson.toJson(mMessageParser.parse(mMessage)));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
