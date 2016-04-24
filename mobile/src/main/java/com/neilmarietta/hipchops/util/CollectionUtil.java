package com.neilmarietta.hipchops.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionUtil {

    public static <T> List<T> getUnmodifiableList(List<T> list) {
        return (list == null) ?
                Collections.unmodifiableList(new ArrayList<T>()) :
                Collections.unmodifiableList(list);
    }
}
