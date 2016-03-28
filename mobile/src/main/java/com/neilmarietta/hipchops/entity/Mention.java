package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;

public class Mention {

    @SerializedName("name")
    private String mName;

    public Mention(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
