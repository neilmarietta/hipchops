package com.neilmarietta.hipchops.presentation.presenter;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.contract.IOMessageListContract;
import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.entity.Emoticon;
import com.neilmarietta.hipchops.entity.Link;
import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.interactor.MessageUseCase;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.util.MessageParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class IOMessageListPresenterTest {

    private Gson mGson;
    private MessageUseCase mMessageUseCase;
    private IOMessageListPresenter mMessageListPresenter;

    private String mInput = "@test (test) test.com";

    @Mock MessageParser mMessageParser;
    @Mock EmoticonRepository mEmoticonRepository;
    @Mock IOMessageListContract.View mMessageListView;

    @Before
    public void setup() {
        initMocks(this);

        mGson = new Gson();

        mMessageUseCase = new MessageUseCase(mGson, mMessageParser, mEmoticonRepository);
        mMessageUseCase.setSubscribeOn(Schedulers.immediate());
        mMessageUseCase.setObserveOn(Schedulers.immediate());

        mMessageListPresenter = new IOMessageListPresenter(mMessageUseCase);
        mMessageListPresenter.setView(mMessageListView);

        Message message = new Message();
        message.addMention("test");
        message.addEmoticon("test");
        message.addLink(new Link("test", "test.com"));

        prepareMocks(mInput, mGson.toJson(message));
    }

    private void prepareMocks(String input, String jsonOutput) {
        Message message = mGson.fromJson(jsonOutput, Message.class);

        when(mMessageParser.parse(input))
                .thenReturn(message);

        if (message.getEmoticons() != null)
            for (String shortcut : message.getEmoticons())
                when(mEmoticonRepository.getEmoticon(shortcut))
                        .thenReturn(Observable.just(new Emoticon(shortcut)));
    }

    @After
    public void tearDown() {
        mMessageListPresenter.destroy();
        mMessageUseCase.unsubscribe();
    }

    @Test
    public void addMessageTest() {
        mMessageListPresenter.addMessage(mInput);

        verify(mMessageListView).showLoading();
        verify(mMessageListView).addMessage(any(IOMessage.class));
        verify(mMessageListView).hideLoading();
    }

    @Test
    public void addMessageErrorTest() {
        mMessageListPresenter.addMessage("unknown");

        verify(mMessageListView).showLoading();
        verify(mMessageListView).hideLoading();
        verify(mMessageListView).showError(anyString());
    }
}
