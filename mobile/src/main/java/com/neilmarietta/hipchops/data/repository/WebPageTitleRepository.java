package com.neilmarietta.hipchops.data.repository;

import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleProvider;
import com.neilmarietta.hipchops.data.repository.provider.WebPageTitleProviderFactory;

import java.io.IOException;

import javax.inject.Inject;

public class WebPageTitleRepository implements WebPageTitleProvider {

    private final WebPageTitleProviderFactory mWebPageTitleProviderFactory;

    @Inject
    public WebPageTitleRepository(WebPageTitleProviderFactory webPageTitleProviderFactory) {
        mWebPageTitleProviderFactory = webPageTitleProviderFactory;
    }

    @Override
    public String getWebPageTitle(String url) throws IOException {
        return mWebPageTitleProviderFactory.create(url).getWebPageTitle(url);
    }
}
