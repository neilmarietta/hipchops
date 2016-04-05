package com.neilmarietta.hipchops.data.cache;

import com.neilmarietta.hipchops.entity.Emoticon;

public class EmoticonCache extends GenericLruCache<Emoticon> {

    public EmoticonCache() {
        super(100); // 100 entries
    }
}
