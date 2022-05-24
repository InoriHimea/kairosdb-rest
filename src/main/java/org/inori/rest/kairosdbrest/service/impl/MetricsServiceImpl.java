package org.inori.rest.kairosdbrest.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.inori.rest.kairosdbrest.model.MetricData;
import org.inori.rest.kairosdbrest.service.MetricsService;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.Metric;
import org.kairosdb.client.builder.MetricBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 10:49 5月
 * @since 1.8
 */
@Service
@Log4j2
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    private HttpClient kairosClient;

    @Override
    public boolean pushMetric(MetricData metricData) {
        MetricBuilder metricBuilder = MetricBuilder.getInstance();
        metricBuilder.setCompression(true);
        String metricName = metricData.getMetricName();
        Assert.isTrue(StringUtils.isNotBlank(metricName), "指标名称不能为空");
        Metric metric = metricBuilder.addMetric(metricName)
                .addTags(metricData.getTags());

        List<Long> longDataPoints = metricData.getLongDataPoints();
        if (CollectionUtils.isNotEmpty(longDataPoints)) {
            longDataPoints.forEach(metric::addDataPoint);
        }

        List<Double> doubleDataPoints = metricData.getDoubleDataPoints();
        if (CollectionUtils.isNotEmpty(doubleDataPoints)) {
            doubleDataPoints.forEach(metric::addDataPoint);
        }

        List<Map<Long, Long>> longDataPointsWithTime = metricData.getLongDataPointsWithTime();
        if (CollectionUtils.isNotEmpty(longDataPointsWithTime)) {
            longDataPointsWithTime.forEach(longMap -> longMap.forEach((key, value) -> metric.addDataPoint(key, Optional.ofNullable(value))));
        }

        List<Map<Long, Double>> doubleDataPointsWithTime = metricData.getDoubleDataPointsWithTime();
        if (CollectionUtils.isNotEmpty(doubleDataPointsWithTime)) {
            doubleDataPointsWithTime.forEach(doubleMap -> doubleMap.forEach((key, value) -> metric.addDataPoint(key, Optional.ofNullable(value))));
        }

        kairosClient.pushMetrics(metricBuilder);
        return Boolean.TRUE;
    }
}
