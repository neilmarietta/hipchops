package com.neilmarietta.hipchops.util;

import com.neilmarietta.hipchops.data.repository.WebPageTitleRepository;
import com.neilmarietta.hipchops.entity.Link;
import com.neilmarietta.hipchops.entity.Message;

import java.io.IOException;
import java.util.regex.Matcher;

public class MessageParser {

    private WebPageTitleRepository mWebPageTitleRepository;

    public MessageParser(WebPageTitleRepository webPageTitleRepository) {
        mWebPageTitleRepository = webPageTitleRepository;
    }

    public Message parse(String input) {
        Message message = new Message();

        Matcher mentions = Regex.MENTION.matcher(input);
        while (mentions.find()) {
            String mention = mentions.group();
            // Remove @
            mention = mention.substring(1, mention.length());
            message.addMention(mention);
        }

        Matcher emoticons = Regex.EMOTICON.matcher(input);
        while (emoticons.find()) {
            String emoticon = emoticons.group();
            // Remove ()
            emoticon = emoticon.substring(1, emoticon.length() - 1);
            message.addEmoticon(emoticon);
        }

        Matcher links = Regex.LINK.matcher(input);
        while (links.find()) {
            String link = links.group();
            String title;
            try {
                title = mWebPageTitleRepository.getWebPageTitle(link);
            } catch (IOException e) {
                title = e.getMessage();
            }
            message.addLink(new Link(title, link));
        }

        return message;
    }
}
