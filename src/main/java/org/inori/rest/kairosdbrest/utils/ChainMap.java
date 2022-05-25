package org.inori.rest.kairosdbrest.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/25 17:31 5月
 * @since 1.8
 */
public class ChainMap<K, V> extends LinkedHashMap<K, V> {

    private final Map<K, V> map = new LinkedHashMap<>();

    public ChainMap<K, V> linkPut(K key, V value) {
        map.put(key, value);
        return this;
    }

    public Map<K, V> getMap() {
        return this.map;
    }
}
