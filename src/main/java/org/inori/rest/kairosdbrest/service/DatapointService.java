package org.inori.rest.kairosdbrest.service;

import org.inori.rest.kairosdbrest.model.QueryParam;
import org.kairosdb.client.response.QueryResult;
import org.kairosdb.client.response.TagQueryResult;

import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 15:36 5月
 * @since 1.8
 */
public interface DatapointService {

    List<QueryResult> query(QueryParam queryParam);

    List<TagQueryResult> queryTags(QueryParam queryParam);

    List<String> listMetricsName();

    Map<String, Boolean> checkMetricExists(List<String> metricsNameList);

    boolean deleteMetric(String metricName);

    boolean deleteMetricPoints(QueryParam queryParam);
}
