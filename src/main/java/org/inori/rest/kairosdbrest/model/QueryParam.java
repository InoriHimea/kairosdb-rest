package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.kairosdb.client.builder.Aggregator;
import org.kairosdb.client.builder.Grouper;

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

    private String metricName;

    private Map<String, String> tags;

    private Map<String, List<String>> multiValueTags;

    private List<Aggregator> Aggregators;

    private List<Grouper> groupers;
}
