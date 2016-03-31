package com.neilmarietta.hipchops.interactor;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.InputOutput;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.util.MessageParser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class GetInputMessageListUseCase extends UseCase {

    private static Gson sGson = new Gson();

    private final MessageParser mMessageParser;

    @Inject
    public GetInputMessageListUseCase(MessageParser messageParser) {
        mMessageParser = messageParser;
    }

    public Observable buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<IOMessage>>() {
            @Override
            public void call(final Subscriber<? super List<IOMessage>> subscriber) {
                final List<IOMessage> messages = new ArrayList<>();

                for (final InputOutput current : TestCases.sTestArray) {
                    new ParseMessageUseCase(current.in, mMessageParser).execute(new Subscriber<Message>() {
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
                        public void onNext(Message result) {
                            // TODO : Do not do toJson on mainThread
                            String output = sGson.toJson(result);

                            messages.add(new IOMessage(current.in, output, result));
                            subscriber.onNext(messages);
                        }
                    });
                }
            }
        });
    }
}
