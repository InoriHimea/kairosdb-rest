package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.inori.rest.kairosdbrest.enums.GrouperEnums;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 10:29 6月
 * @since 1.8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Groupers {

    private GrouperEnums grouperName;

    private GrouperWrapper grouperContent;
}
