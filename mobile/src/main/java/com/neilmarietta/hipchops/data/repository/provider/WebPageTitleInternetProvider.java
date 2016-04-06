package com.neilmarietta.hipchops.data.repository.provider;

import com.neilmarietta.hipchops.util.Regex;

import java.io.IOException;
import java.util.regex.Matcher;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * WebPageTitleProvider with Internet connection.
 */
public class WebPageTitleInternetProvider implements WebPageTitleProvider {

    private static String HTTP_PREFIX = "http://";
    private static String HTTPS_PREFIX = "https://";

    private final WebPageTitleLocalProvider mWebPageTitleLocalProvider;

    public WebPageTitleInternetProvider(WebPageTitleLocalProvider webPageTitleLocalProvider) {
        mWebPageTitleLocalProvider = webPageTitleLocalProvider;
    }

    @Override
    public String getWebPageTitle(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX) ? url : HTTP_PREFIX + url)
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();

        MediaType contentType = body.contentType();

        if (contentType.type().equals("text") && contentType.subtype().equals("html")) {
            Matcher matcher = Regex.HTML_TITLE.matcher(body.string());
            if (matcher.find()) {
                String pageTitle = matcher.group(1).replace("\u0026quot;", "");

                // Remember pageTitle
                mWebPageTitleLocalProvider.put(url, pageTitle);

                return pageTitle;
            }
        }

        return null;
    }
}
