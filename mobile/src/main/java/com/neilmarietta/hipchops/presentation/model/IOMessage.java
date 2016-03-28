package com.neilmarietta.hipchops.presentation.model;

public class IOMessage {

    private String mInput;
    private String mJsonOutput;

    public IOMessage(String input, String jsonOutput) {
        mInput = input;
        mJsonOutput = jsonOutput;
    }

    public String getInput() {
        return mInput;
    }

    public String getJsonOutput() {
        return mJsonOutput;
    }
}
