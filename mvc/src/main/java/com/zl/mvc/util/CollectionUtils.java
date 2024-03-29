package com.zl.mvc.util;

import com.zl.mvc.support.MultiValueMap;
import com.zl.mvc.support.MultiValueMapAdapter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class CollectionUtils {

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 合并数组（第一个参数）的成员到集合（第二个参数）里面去
     * @param array
     * @param collection
     * @param <E>
     */
    public static <E> void mergeArrayIntoCollection(Object array, Collection<E> collection) {
        Object[] arr = ObjectUtils.toObjectArray(array);
        for (Object elem : arr) {
            collection.add((E) elem);
        }
    }
    public static <K, V> MultiValueMap<K, V> toMultiValueMap(Map<K, List<V>> targetMap) {
        return new MultiValueMapAdapter<>(targetMap);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int expectedSize) {
        return new LinkedHashMap<>((int) (expectedSize / DEFAULT_LOAD_FACTOR), DEFAULT_LOAD_FACTOR);
    }
}
