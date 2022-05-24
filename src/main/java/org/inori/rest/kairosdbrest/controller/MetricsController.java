package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.annotation.ApiDoc;
import org.inori.rest.kairosdbrest.model.MetricData;
import org.inori.rest.kairosdbrest.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 16:06 5月
 * @since 1.8
 */
@RestController
@Log4j2
@RequestMapping("/metrics")
@ApiDoc
public class MetricsController {

    @Autowired
    private MetricsService metricsService;

    @PostMapping("/push")
    public Mono<Boolean> pushMetric(@RequestBody MetricData metricData) {
        return Mono.just(metricsService.pushMetric(metricData))
                .onErrorReturn(Boolean.FALSE);
    }

    @PostMapping("/batch/push")
    public Mono<Map<String, Boolean>> pushMetrics(@RequestBody List<MetricData> metricDataList) {
        return Mono.just(metricsService.pushMetrics(metricDataList))
                .onErrorReturn(Collections.emptyMap());
    }
}
