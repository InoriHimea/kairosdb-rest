package org.inori.rest.kairosdbrest.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 10:36 6月
 * @since 1.8
 */
@AllArgsConstructor
@Getter
@ToString
public enum GrouperEnums {

    TAG("tag"),
    BIN("bing"),
    VALUE("value"),
    TIME("time"),
    CUSTOM("custom");

    @JsonValue
    private final String jsonValue;
}
