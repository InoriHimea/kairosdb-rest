package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.inori.rest.kairosdbrest.enums.AggregatorEnums;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 11:37 6月
 * @since 1.8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Aggregators {

    private AggregatorEnums aggregatorName;

    private AggregatorWrapper grouperContent;
}
