package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;

public class Emoticon {

    @SerializedName("shortcut")
    private String mShortcut;

    public Emoticon(String shortcut) {
        mShortcut = shortcut;
    }

    public String getShortcut() {
        return mShortcut;
    }
}
