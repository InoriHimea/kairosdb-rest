package org.inori.rest.kairosdbrest.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 12:01 6月
 * @since 1.8
 */
@AllArgsConstructor
@Getter
@ToString
public enum AggregatorEnums {

    AVG("avg"),
    COUNT("count"),
    DEV("dev"),
    DIFF("diff"),
    DIV("div"),
    FILTER("filter"),
    FIRST("first"),
    GAPS("gaps"),
    LAST("list"),
    LEAST_SQUARES("least_squares"),
    MAX("max"),
    MIN("min"),
    PERCENTILE("percentile"),
    RATE("rate"),
    SAMPLER("sampler"),
    SAVE_AS("save_as"),
    SCALE("scale"),
    SUM("sum"),
    TRIM("trim"),
    CUSTOM("custom");

    @JsonValue
    private final String jsonValue;
}
