package com.neilmarietta.hipchops.entity;

import com.google.gson.annotations.SerializedName;
import com.neilmarietta.hipchops.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class Message {

    // List could be null
    @SerializedName("mentions")
    private List<String> mMentions;

    // List could be null
    @SerializedName("emoticons")
    private List<String> mEmoticons;

    // List could be null
    @SerializedName("links")
    private List<Link> mLinks;

    public List<String> getMentions() {
        return CollectionUtil.getUnmodifiableList(mMentions);
    }

    public List<String> getEmoticons() {
        return CollectionUtil.getUnmodifiableList(mEmoticons);
    }

    public List<Link> getLinks() {
        return CollectionUtil.getUnmodifiableList(mLinks);
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
