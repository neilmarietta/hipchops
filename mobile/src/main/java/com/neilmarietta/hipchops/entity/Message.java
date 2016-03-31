package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Message {

    @SerializedName("mentions")
    private List<String> mMentions;

    @SerializedName("emoticons")
    private List<String> mEmoticons;

    @SerializedName("links")
    private List<Link> mLinks;

    public List<String> getMentions() {
        return mMentions;
    }

    public List<String> getEmoticons() {
        return mEmoticons;
    }

    public List<Link> getLinks() {
        return mLinks;
    }

    public void addMention(String mention) {
        if (mMentions == null)
            mMentions = new ArrayList<>();

        mMentions.add(mention);
    }

    public void addEmoticon(String emoticon) {
        if (mEmoticons == null)
            mEmoticons = new ArrayList<>();

        mEmoticons.add(emoticon);
    }

    public void addLink(Link link) {
        if (mLinks == null)
            mLinks = new ArrayList<>();

        mLinks.add(link);
    }
}
