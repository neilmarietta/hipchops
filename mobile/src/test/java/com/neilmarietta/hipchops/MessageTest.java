package com.neilmarietta.hipchops;

import com.google.gson.Gson;
import com.neilmarietta.hipchops.data.TestCases;
import com.neilmarietta.hipchops.entity.Link;
import com.neilmarietta.hipchops.entity.Message;
import com.neilmarietta.hipchops.util.MessageParser;
import com.neilmarietta.hipchops.util.WebPageTitleLocalProvider;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    private static Gson sGson = new Gson();
    private static MessageParser sParser = new MessageParser(new WebPageTitleLocalProvider());

    private void assertMessageEquals(String expectedJson, String actualInput) {
        // Avoid different JSON indentation/format/order
        try {
            assertEquals(
                    // By regenerating JSON from JSON
                    sGson.toJson(sGson.fromJson(expectedJson, Message.class)),
                    sGson.toJson(sParser.parse(actualInput)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assertMessageEquals(Message expectedMessage, String actualInput) {
        try {
            assertEquals(
                    sGson.toJson(expectedMessage),
                    sGson.toJson(sParser.parse(actualInput)));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
