package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;

public class Emoticon {

    @SerializedName("shortcut")
    private String mShortcut;

    @SerializedName("id")
    private int mId;

    @SerializedName("type")
    private String mType;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("height")
    private int mHeight;

    @SerializedName("width")
    private int mWidth;

    @SerializedName("creator")
    private Creator mCreator;

    public Emoticon(String shortcut) {
        mShortcut = shortcut;
    }

    public Emoticon(String shortcut, int id, String type, String url, int height, int width, Creator creator) {
        mShortcut = shortcut;
        mId = id;
        mType = type;
        mUrl = url;
        mHeight = height;
        mWidth = width;
        mCreator = creator;
    }

    public String getShortcut() {
        return mShortcut;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public Creator getCreator() {
        return mCreator;
    }
}
