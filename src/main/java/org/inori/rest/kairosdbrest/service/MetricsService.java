package org.inori.rest.kairosdbrest.service;

import org.inori.rest.kairosdbrest.model.MetricData;

import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 10:48 5月
 * @since 1.8
 */
public interface MetricsService {

    boolean pushMetric(MetricData metricData);

    Map<String, Boolean> pushMetrics(List<MetricData> metricDataList);
}
