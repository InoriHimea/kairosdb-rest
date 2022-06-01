package org.inori.rest.kairosdbrest.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.inori.rest.kairosdbrest.enums.AlignType;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.builder.aggregator.*;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 14:18 6月
 * @since 1.8
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AggregatorWrapper {

    private Integer samplingValue;
    private TimeUnit samplingUnit;
    private AlignType alignType = AlignType.START_TIME;
    private TimeUnit rateUnit;
    private Double percentile;
    private Integer percentileValue;
    private TimeUnit percentileUnit;
    private Map<String, Object> properties;
    private String json;
    private String customAggregatorName;

    public SamplingAggregator getSamplingAggregator(String name) {
        Assert.notNull(samplingValue, "Sampling类型的aggregator需要指定value属性");
        Assert.notNull(samplingUnit, "Sampling类型的aggregator需要指定unit属性");
        SamplingAggregator samplingAggregator = new SamplingAggregator(name, this.samplingValue, this.samplingUnit);

        switch (alignType) {
            case START_TIME:
            default:
                samplingAggregator.withSamplingAlignment().withStartTimeAlignment();
                break;
            case END_TIME:
                samplingAggregator.withSamplingAlignment().withEndTimeAlignment();
                break;
            case NONE:
                break;
        }

        return samplingAggregator;
    }

    public RateAggregator getRateAggregator() {
        Assert.notNull(samplingUnit, "Rate类型的aggregator需要指定unit属性");
        return new RateAggregator(this.rateUnit);
    }

    public PercentileAggregator getPercentileAggregator() {
        Assert.notNull(percentile, "Percentile类型的aggregator需要指定percentile属性");
        Assert.notNull(percentileValue, "Percentile类型的aggregator需要指定value属性");
        Assert.notNull(percentileUnit, "Percentile类型的aggregator需要指定unit属性");
        return new PercentileAggregator(this.percentile, this.percentileValue, this.percentileUnit);
    }

    public DeserializedAggregator getDeserializedAggregator() {
        Assert.isTrue(MapUtils.isNotEmpty(properties), "Deserialized类型的aggregator需要指定properties(key-value)属性");
        return new DeserializedAggregator(this.properties);
    }

    public CustomAggregator getCustomAggregator() {
        Assert.notNull(customAggregatorName, "Custom类型的aggregator需要指定name属性");
        Assert.isTrue(StringUtils.isNotBlank(json), "Custom类型的aggregator需要指定json属性");
        return new CustomAggregator(this.customAggregatorName, this.json);
    }
}
