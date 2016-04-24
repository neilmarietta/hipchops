package com.neilmarietta.hipchops.presentation.model;

import com.neilmarietta.hipchops.entity.Message;

public class IOMessage {

    private String mInput;
    private String mJsonOutput;
    private Message mMessage;

    public IOMessage(String input, String jsonOutput, Message message) {
        mInput = input;
        mJsonOutput = jsonOutput;
        mMessage = message;
    }

    public String getInput() {
        return mInput;
    }

    public String getJsonOutput() {
        return mJsonOutput;
    }

    public Message getMessage() {
        return mMessage;
    }
}
