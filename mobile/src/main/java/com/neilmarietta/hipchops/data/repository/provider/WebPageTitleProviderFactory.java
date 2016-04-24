package com.neilmarietta.hipchops.data.repository.provider;

import javax.inject.Inject;

public class WebPageTitleProviderFactory {

    private final WebPageTitleLocalProvider mWebPageTitleLocalProvider;

    @Inject
    public WebPageTitleProviderFactory(WebPageTitleLocalProvider webPageTitleLocalProvider) {
        mWebPageTitleLocalProvider = webPageTitleLocalProvider;
    }

    public WebPageTitleProvider create(String url) {
        return mWebPageTitleLocalProvider.inProvider(url) ?
                createWebPageTitleLocalProvider() :
                createWebPageTitleInternetProvider();
    }

    public WebPageTitleProvider createWebPageTitleLocalProvider() {
        return mWebPageTitleLocalProvider;
    }

    public WebPageTitleProvider createWebPageTitleInternetProvider() {
        return new WebPageTitleInternetProvider(mWebPageTitleLocalProvider);
    }
}