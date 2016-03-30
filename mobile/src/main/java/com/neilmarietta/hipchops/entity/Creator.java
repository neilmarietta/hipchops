package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;

public class Creator {

    @SerializedName("id")
    private int mId;

    @SerializedName("links")
    private Link mLink;

    @SerializedName("mention_name")
    private String mMentionName;

    @SerializedName("name")
    private String mName;

    @SerializedName("version")
    private String mVersion;

    public Creator(int id) {
        mId = id;
    }

    public Creator(int id, Link link, String mentionName, String name, String version) {
        mId = id;
        mLink = link;
        mMentionName = mentionName;
        mName = name;
        mVersion = version;
    }

    public int getId() {
        return mId;
    }

    public Link getLink() {
        return mLink;
    }

    public String getMentionName() {
        return mMentionName;
    }

    public String getName() {
        return mName;
    }

    public String getVersion() {
        return mVersion;
    }
}
