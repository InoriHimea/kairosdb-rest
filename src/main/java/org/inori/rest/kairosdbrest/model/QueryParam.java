package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.kairosdb.client.builder.QueryMetric;

import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/26 16:09 5月
 * @since 1.8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueryParam {

    private String startTime;

    private String endTime;

    private String startDuring;

    private String endDuring;

    private String metricName;

    private Integer limit;

    private QueryMetric.Order order;

    private Map<String, String> tags;

    private Map<String, List<String>> multiValueTags;

    private List<Aggregators> Aggregators;

    private List<Groupers> groupers;
}
