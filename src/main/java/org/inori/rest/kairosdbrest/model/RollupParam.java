package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/1 13:39 6月
 * @since 1.8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RollupParam {

    private String taskName;
    private String relativeTime;
    private String saveAs;
    private QueryParam queryParam;
}
