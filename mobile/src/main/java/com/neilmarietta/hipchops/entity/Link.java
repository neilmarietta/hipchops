package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;

public class Link {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    public Link(String title, String url) {
        mTitle = title;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
