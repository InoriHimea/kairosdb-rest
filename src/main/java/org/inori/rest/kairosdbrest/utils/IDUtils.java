package org.inori.rest.kairosdbrest.utils;

import java.util.UUID;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/11 14:50 5月
 * @since 1.8
 */
public class IDUtils {

    public static String getId() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    public static String getSimpleId() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }
}
