package org.inori.rest.kairosdbrest.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.inori.rest.kairosdbrest.model.QueryParam;
import org.inori.rest.kairosdbrest.service.DatapointService;
import org.inori.rest.kairosdbrest.utils.TimeUtil;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.Aggregator;
import org.kairosdb.client.builder.Grouper;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.QueryMetric;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 15:36 5月
 * @since 1.8
 */
@Service
@Log4j2
public class DatapointServiceImpl implements DatapointService {

    @Autowired
    private HttpClient kairosClient;

    @Override
    public List<QueryResult> query(QueryParam queryParam) {
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        String metricName = queryParam.getMetricName();
        String startTime = queryParam.getStartTime();
        Assert.isTrue(StringUtils.isNotBlank(metricName), "指标名称不能为空");

        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        QueryMetric queryMetric = queryBuilder.setStart(TimeUtil.toDate(startDateTime))
                .addMetric(metricName);

        Map<String, String> tags = queryParam.getTags();
        if (MapUtils.isNotEmpty(tags)) {
            queryMetric.addTags(tags);
        }

        List<Aggregator> aggregators = queryParam.getAggregators();
        if (CollectionUtils.isNotEmpty(aggregators)) {
            aggregators.forEach(queryMetric::addAggregator);
        }

        Map<String, List<String>> multiValueTags = queryParam.getMultiValueTags();
        if (MapUtils.isNotEmpty(multiValueTags)) {
            queryMetric.addMultiValuedTags(multiValueTags);
        }

        List<Grouper> groupers = queryParam.getGroupers();
        if (CollectionUtils.isNotEmpty(groupers)) {
            groupers.forEach(queryMetric::addGrouper);
        }

        try {
            QueryResponse queryResponse = kairosClient.query(queryBuilder);
            return queryResponse.getQueries();
        } catch (Exception e) {
            log.error("查询异常: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
