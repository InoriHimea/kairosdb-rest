package org.inori.rest.kairosdbrest.service;

import org.inori.rest.kairosdbrest.model.MetricData;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 10:48 5月
 * @since 1.8
 */
public interface MetricsService {

    boolean pushMetric(MetricData metricData);
}
