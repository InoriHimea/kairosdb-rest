package org.inori.rest.kairosdbrest.service;

import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 17:37 5月
 * @since 1.8
 */
public interface InfoService {

    Boolean status();

    List<String> listStatus();

    int checkStatus();

    String version();
}
