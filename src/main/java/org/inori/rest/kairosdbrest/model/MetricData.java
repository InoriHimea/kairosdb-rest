package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 11:44 5月
 * @since 1.8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MetricData {

    /**
     * 指标名
     */
    private String metricName;

    /**
     * 指标的标签
     */
    private Map<String, String> tags;

    private List<Long> longDataPoints;

    private List<Double> doubleDataPoints;

    private List<Map<Long, Long>> longDataPointsWithTime;

    private List<Map<Long, Double>> doubleDataPointsWithTime;

    private List<Map<Long, Object>> objectDataPointsWithTime;
}
