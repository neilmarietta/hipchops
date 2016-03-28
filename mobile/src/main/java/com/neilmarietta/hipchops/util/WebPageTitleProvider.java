package com.neilmarietta.hipchops.util;

import java.io.IOException;

public interface WebPageTitleProvider {
    String getWebPageTitle(String url) throws IOException;
}