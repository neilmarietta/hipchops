package com.neilmarietta.hipchops.interactor;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.InputOutput;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.data.repository.EmoticonRepository;
import com.neilmarietta.hipchops.entity.Emoticon;
import com.neilmarietta.hipchops.entity.Link;
import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.internal.di.component.DaggerTestMessageComponent;
import com.neilmarietta.hipchops.presentation.model.EmoticonUrls;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.util.MessageParser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public class MessageUseCaseTest {

    @Inject MessageUseCase mMessageUseCase;
    @Inject MessageParser mMessageParser;
    @Inject EmoticonRepository mEmoticonRepository;
    @Inject Gson mGson;

    @Before
    public void setup() {
        DaggerTestMessageComponent.create().inject(this);
        mMessageUseCase.setSubscribeOn(Schedulers.immediate());
        mMessageUseCase.setObserveOn(Schedulers.immediate());
    }

    @After
    public void tearDown() {
        mMessageUseCase.unsubscribe();
        mMessageUseCase = null;
        mMessageParser = null;
        mEmoticonRepository = null;
        mGson = null;
    }

    private void assertEquals(IOMessage expected, IOMessage actual) {
        Assert.assertEquals(expected.getInput(), actual.getInput());
        Assert.assertEquals(expected.getJsonOutput(), actual.getJsonOutput());
        Assert.assertEquals(expected.getMessage().getEmoticons(), actual.getMessage().getEmoticons());
        Assert.assertEquals(expected.getMessage().getMentions(), actual.getMessage().getMentions());

        Assert.assertEquals(expected.getMessage().getLinks().size(), actual.getMessage().getLinks().size());
        for (int i = 0; i < expected.getMessage().getLinks().size(); i++) {
            Assert.assertEquals(
                    expected.getMessage().getLinks().get(i).getTitle(),
                    actual.getMessage().getLinks().get(i).getTitle());
            Assert.assertEquals(
                    expected.getMessage().getLinks().get(i).getUrl(),
                    actual.getMessage().getLinks().get(i).getUrl());
        }
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

    @Test
    public void getMessageTest() {
        String input = "@test (test) test.com";

        Message message = new Message();
        message.addMention("test");
        message.addEmoticon("test");
        message.addLink(new Link("test", "test.com"));

        prepareMocks(input, mGson.toJson(message));

        // Subscribe Observable
        TestSubscriber<IOMessage> testSubscriber = new TestSubscriber<>();
        mMessageUseCase.getMessage(input, testSubscriber);
        testSubscriber.assertNoErrors();

        IOMessage expected = new IOMessage(input, mGson.toJson(message), message);
        IOMessage actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(expected, actual);
    }

    @Test
    public void getMessageListTest() {
        for (InputOutput inputOutput : TestCases.sTestArray)
            prepareMocks(inputOutput.in, inputOutput.out);

        Message[] messages = new Message[TestCases.sTestArray.length];
        for (int i = 0; i < TestCases.sTestArray.length; i++)
            messages[i] = mGson.fromJson(TestCases.sTestArray[i].out, Message.class);

        // Subscribe Observable
        TestSubscriber<IOMessage> testSubscriber = new TestSubscriber<>();
        mMessageUseCase.getMessageList(TestCases.getInputs(), testSubscriber);
        testSubscriber.assertNoErrors();

        for (int i = 0; i < TestCases.sTestArray.length; i++) {
            IOMessage expected = new IOMessage(TestCases.sTestArray[i].in, mGson.toJson(messages[i]), messages[i]);
            IOMessage actual = testSubscriber.getOnNextEvents().get(i);

            assertEquals(expected, actual);
        }
    }

    @Test
    public void fetchEmoticonsTest() {
        String input = "(test) (test) (test1) (test2)";

        Message message = new Message();
        message.addEmoticon("test");
        message.addEmoticon("test");
        message.addEmoticon("test1");
        message.addEmoticon("test2");

        prepareMocks(input, mGson.toJson(message));

        // Subscribe Observable
        TestSubscriber<IOMessage> testSubscriber = new TestSubscriber<>();
        mMessageUseCase.getMessage(input, testSubscriber);
        testSubscriber.assertNoErrors();

        IOMessage expected = new IOMessage(input, mGson.toJson(message), message);
        IOMessage actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(expected, actual);

        Assert.assertTrue(EmoticonUrls.CACHE.containsKey("test"));
        Assert.assertTrue(EmoticonUrls.CACHE.containsKey("test1"));
        Assert.assertTrue(EmoticonUrls.CACHE.containsKey("test2"));
        Assert.assertFalse(EmoticonUrls.CACHE.containsKey("unknown"));
    }
}
