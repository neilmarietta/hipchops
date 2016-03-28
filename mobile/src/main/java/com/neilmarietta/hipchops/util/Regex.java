package com.neilmarietta.hipchops.util;

import java.util.regex.Pattern;

public class Regex {

    /**
     * Emoticon Pattern : Alphanumeric, max 15 characters, contained in parenthesis.<p>
     * Examples : "(smile) (success)".
     */
    public static Pattern EMOTICON = Pattern.compile("\\((\\w{1,15})\\)");

    /**
     * Link Pattern : Any URLs.<p>
     * Examples : "http://www.example.com, https://example.com, www.demo.com, demo.com".
     */
    public static Pattern LINK = Pattern.compile("((http|https)\\:\\/\\/||www)[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,5}(\\/\\S*)?");

    /**
     * Mention Pattern : starts with an '@' and ends when hitting a non-word character.<p>
     * Examples : "@mention, @neilmarietta".
     */
    public static Pattern MENTION = Pattern.compile("(@|\\uFF20)(\\w+)");

    /**
     * HTML Title Tag Pattern.
     */
    public static Pattern HTML_TITLE = Pattern.compile("<title>(.*)</title>", Pattern.CASE_INSENSITIVE);
}