package org.inori.rest.kairosdbrest.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/31 18:08 5月
 * @since 1.8
 */
@AllArgsConstructor
@Getter
@ToString
public enum AlignType {

    NONE("none"),
    START_TIME("start_time"),
    END_TIME("end_time");

    @JsonValue
    private final String jsonValue;
}
