package com.neilmarietta.hipchops.util;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.data.repository.WebPageTitleRepository;
import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleLocalProvider;
import com.neilmarietta.hipchops.entity.Link;
import com.neilmarietta.hipchops.entity.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessageParserTest {

    private Gson mGson;
    private MessageParser mMessageParser;

    @Mock WebPageTitleRepository mTitleRepository;

    @Before
    public void setup() throws IOException {
        initMocks(this);

        mGson = new Gson();

        mMessageParser = new MessageParser(mTitleRepository);

        WebPageTitleLocalProvider localProvider = new WebPageTitleLocalProvider();

        // Add all known local titles
        for (String key : localProvider.keySet())
            when(mTitleRepository.getWebPageTitle(key))
                    .thenReturn(localProvider.getWebPageTitle(key));
    }

    @After
    public void tearDown() {
    }

    private void assertMessageEquals(String expectedJson, String actualInput) {
        // Avoid different JSON indentation/format/order
        assertEquals(
                // By regenerating JSON from JSON
                mGson.toJson(mGson.fromJson(expectedJson, Message.class)),
                mGson.toJson(mMessageParser.parse(actualInput)));
    }

    private void assertMessageEquals(Message expectedMessage, String actualInput) {
        assertEquals(
                mGson.toJson(expectedMessage),
                mGson.toJson(mMessageParser.parse(actualInput)));
    }

    @Test
    public void parseInputsTest() {
        assertMessageEquals(TestCases.sTestArray[0].out, TestCases.sTestArray[0].in);
        assertMessageEquals(TestCases.sTestArray[1].out, TestCases.sTestArray[1].in);
        assertMessageEquals(TestCases.sTestArray[2].out, TestCases.sTestArray[2].in);
        assertMessageEquals(TestCases.sTestArray[3].out, TestCases.sTestArray[3].in);
        assertMessageEquals(TestCases.sTestArray[4].out, TestCases.sTestArray[4].in);
    }

    @Test
    public void parseOtherInputsTest() {
        Message message1 = new Message();
        message1.addMention("neil");
        message1.addMention("bob");
        message1.addEmoticon("smile");
        message1.addEmoticon("smile");
        assertMessageEquals(message1, "@neil (smile) @bob (smile)");

        Message message2 = new Message();
        message2.addMention("neil");
        message2.addEmoticon("smile");
        message2.addLink(new Link("Neil Marietta | LinkedIn", "https://www.linkedin.com/in/neilmarietta"));
        assertMessageEquals(message2, TestCases.sTestArray[4].in);
    }
}
