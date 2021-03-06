package com.neilmarietta.hipchops.interactor;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.entity.Emoticon;
import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.presentation.model.EmoticonUrls;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.util.MessageParser;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MessageUseCase extends UseCase {

    private final Gson mGson;
    private final MessageParser mMessageParser;
    private final EmoticonRepository mEmoticonRepository;

    public MessageUseCase(Gson gson, MessageParser messageParser, EmoticonRepository emoticonRepository) {
        mGson = gson;
        mMessageParser = messageParser;
        mEmoticonRepository = emoticonRepository;
    }

    public void getMessage(String input, Subscriber subscriber) {
        execute(getMessage(input), subscriber);
    }

    public void getMessageList(List<String> messages, Subscriber subscriber) {
        execute(getMessageList(messages), subscriber);
    }

    private Observable<Message> parseMessage(String message) {
        return Observable
                .just(message)
                .map(new Func1<String, Message>() {
                    @Override
                    public Message call(String input) {
                        return mMessageParser.parse(input);
                    }
                });
    }

    private Observable<IOMessage> getMessage(String input) {
        return Observable
                .just(input)
                .concatMap(new Func1<String, Observable<IOMessage>>() {
                    @Override
                    public Observable<IOMessage> call(final String input) {
                        return parseMessage(input)
                                .map(new Func1<Message, IOMessage>() {
                                    @Override
                                    public IOMessage call(Message output) {
                                        return new IOMessage(input, mGson.toJson(output), output);
                                    }
                                })
                                .concatMap(new Func1<IOMessage, Observable<IOMessage>>() {
                                    @Override
                                    public Observable<IOMessage> call(IOMessage message) {
                                        return fetchEmoticons(message);
                                    }
                                });
                    }
                });
    }

    private Observable<IOMessage> fetchEmoticons(final IOMessage message) {
        return Observable
                .just(message)
                .flatMapIterable(new Func1<IOMessage, Iterable<String>>() {
                    @Override
                    public Iterable<String> call(IOMessage message) {
                        return EmoticonUrls.getMissingEmoticonUrlShortcuts(message);
                    }
                })
                .flatMap(new Func1<String, Observable<Emoticon>>() {
                    @Override
                    public Observable<Emoticon> call(final String shortcut) {
                        return mEmoticonRepository.getEmoticon(shortcut)
                                .onErrorReturn(new Func1<Throwable, Emoticon>() {
                                    @Override
                                    public Emoticon call(Throwable throwable) {
                                        return new Emoticon(shortcut);
                                    }
                                });
                    }
                })
                .map(new Func1<Emoticon, IOMessage>() {
                    @Override
                    public IOMessage call(Emoticon emoticon) {
                        EmoticonUrls.CACHE.put(emoticon.getShortcut(), emoticon.getUrl());
                        return message;
                    }
                })
                // Return initial message if no missing emoticons
                .defaultIfEmpty(message)
                // Only emit on the last emoticon
                .last();
    }

    private Observable<IOMessage> getMessageList(List<String> messages) {
        return Observable
                .from(messages)
                .concatMap(new Func1<String, Observable<IOMessage>>() {
                    @Override
                    public Observable<IOMessage> call(final String input) {
                        return getMessage(input);
                    }
                });
    }
}
