package com.neilmarietta.hipchops.presentation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmoticonUrls {

    public static HashMap<String, String> CACHE = new HashMap<>();

    public static List<String> getMissingEmoticonUrlShortcuts(IOMessage message) {
        List<String> missingEmoticons = new ArrayList<>();

        for (String shortcut : message.getMessage().getEmoticons()) {
            if (!CACHE.containsKey(shortcut) && !missingEmoticons.contains(shortcut))
                missingEmoticons.add(shortcut);
        }

        return missingEmoticons;
    }
}
