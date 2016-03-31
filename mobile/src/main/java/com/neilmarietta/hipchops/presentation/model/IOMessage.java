package com.neilmarietta.hipchops.presentation.model;

import com.neilmarietta.hipchops.entity.Emoticon;
import com.neilmarietta.hipchops.entity.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IOMessage {

    private String mInput;
    private String mJsonOutput;
    private Message mMessage;

    private HashMap<String, Emoticon> mEmoticons = new HashMap<>();

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

    public HashMap<String, Emoticon> getEmoticons() {
        return mEmoticons;
    }

    public List<String> getMissingEmoticons() {
        List<String> missingEmoticons = new ArrayList<>();
        List<String> emoticons = getMessage().getEmoticons();

        if (emoticons != null) {
            for (String shortcut : emoticons) {
                if (!mEmoticons.containsKey(shortcut) && !missingEmoticons.contains(shortcut))
                    missingEmoticons.add(shortcut);
            }
        }

        return missingEmoticons;
    }
}
