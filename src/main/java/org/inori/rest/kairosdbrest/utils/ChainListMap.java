package org.inori.rest.kairosdbrest.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/25 17:42 5月
 * @since 1.8
 */
public class ChainListMap<K, V> extends LinkedList<Map<K, V>> {

    private final List<Map<K, V>> list = new LinkedList<>();

    public ChainListMap<K, V> linkAdd(Map<K, V> map) {
        list.add(map);
        return this;
    }

    public List<Map<K, V>> getList() {
        return this.list;
    }
}
