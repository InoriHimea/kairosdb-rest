package org.inori.rest.kairosdbrest.service;

import org.inori.rest.kairosdbrest.model.QueryParam;
import org.kairosdb.client.response.QueryResult;

import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 15:36 5月
 * @since 1.8
 */
public interface DatapointService {

    List<QueryResult> query(QueryParam queryParam);
}
