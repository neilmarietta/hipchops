package com.neilmarietta.hipchops.data.repository.provider;

import java.util.HashMap;
import java.util.Set;

/**
 * WebPageTitleProvider without Internet connection (cached).
 */
public class WebPageTitleLocalProvider implements WebPageTitleProvider {

    private static HashMap<String, String> INTERNET_URLS = new HashMap<>();
    static {
        INTERNET_URLS.put("http://www.nbcolympics.com", "NBC Olympics | Home of the 2016 Olympic Games in Rio");
        INTERNET_URLS.put("https://twitter.com/jdorfman/status/430511497475670016", "Justin Dorfman auf Twitter: nice @littlebigdetail from @HipChat (shows hex colors when pasted in chat). http://t.co/7cI6Gjy5pq");
        INTERNET_URLS.put("https://www.linkedin.com/in/neilmarietta", "Neil Marietta | LinkedIn");
    }

    @Override
    public String getWebPageTitle(String url) {
        return INTERNET_URLS.get(url);
    }

    public boolean inProvider(String url) {
        return INTERNET_URLS.containsKey(url);
    }

    public void put(String url, String pageTitle) {
        INTERNET_URLS.put(url, pageTitle);
    }

    public Set<String> keySet() {
        return INTERNET_URLS.keySet();
    }
}
