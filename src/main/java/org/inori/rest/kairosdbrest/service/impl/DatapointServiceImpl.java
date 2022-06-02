package org.inori.rest.kairosdbrest.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.inori.rest.kairosdbrest.model.QueryParam;
import org.inori.rest.kairosdbrest.service.BaseKairosService;
import org.inori.rest.kairosdbrest.service.DatapointService;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.QueryTagBuilder;
import org.kairosdb.client.builder.QueryTagMetric;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.QueryResult;
import org.kairosdb.client.response.QueryTagResponse;
import org.kairosdb.client.response.TagQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 15:36 5月
 * @since 1.8
 */
@Service
@Log4j2
public class DatapointServiceImpl extends BaseKairosService implements DatapointService {

    @Autowired
    private HttpClient kairosClient;

    @Override
    public List<QueryResult> query(QueryParam queryParam) {
        QueryBuilder queryBuilder = queryParam2QueryBuilder(queryParam);

        try {
            QueryResponse queryResponse = kairosClient.query(queryBuilder);
            return queryResponse.getQueries();
        } catch (Exception e) {
            log.error("查询异常: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<TagQueryResult> queryTags(QueryParam queryParam) {
        QueryTagBuilder tagBuilder = QueryTagBuilder.getInstance();

        checkRequiredParam(queryParam, tagBuilder);

        QueryTagMetric queryTagMetric = tagBuilder.addMetric(queryParam.getMetricName());

        Map<String, String> tags = queryParam.getTags();
        if (MapUtils.isNotEmpty(tags)) {
            queryTagMetric.addTags(tags);
        }

        try {
            QueryTagResponse queryResponse = kairosClient.queryTags(tagBuilder);
            return queryResponse.getQueries();
        } catch (Exception e) {
            log.error("查询异常: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> listMetricsName() {
        return kairosClient.getMetricNames();
    }

    @Override
    public Map<String, Boolean> checkMetricExists(List<String> metricsNameList) {
        List<String> metricNames = kairosClient.getMetricNames();
        return metricsNameList.stream()
                .collect(Collectors.toMap(metric -> metric, metricNames::contains));
    }

    @Override
    public boolean deleteMetric(String metricName) {
        log.info("删除指标: {}", metricName);

        try {
            kairosClient.deleteMetric(metricName);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("删除指标异常: {}", e.getMessage() ,e);
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean deleteMetricPoints(QueryParam queryParam) {
        QueryBuilder queryBuilder = queryParam2QueryBuilder(queryParam);

        try {
            kairosClient.delete(queryBuilder);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("指标点删除异常: {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }
}
