package com.neilmarietta.hipchops.data.repository.provider;

import java.io.IOException;

public interface WebPageTitleProvider {
    String getWebPageTitle(String url) throws IOException;
}