package org.inori.rest.kairosdbrest.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kairosdb.client.builder.RelativeTime;
import org.kairosdb.client.builder.grouper.*;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 装饰器，用于整合数据分发
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/4 11:13 6月
 * @since 1.8
 */
@NoArgsConstructor
@AllArgsConstructor
public class GrouperWrapper {

    private List<String> tagList;
    private List<Double> binList;
    private RelativeTime timeRangeSize;
    private Integer count;
    private Integer valueRangeSize;
    //private String customGrouperName = "custom";
    private String json;

    public TagGrouper getTagGrouper() {
        Assert.isTrue(CollectionUtils.isNotEmpty(tagList), "Tags类型的grouper需要指定tagList属性");
        return new TagGrouper(tagList);
    }

    public BinGrouper getBinGrouper() {
        Assert.isTrue(CollectionUtils.isNotEmpty(binList), "Bin类型的grouper需要指定binList属性");
        return new BinGrouper(binList);
    }

    public TimeGrouper getTimeGrouper() {
        Assert.notNull(timeRangeSize, "Time类型的grouper需要指定timeRangeSize属性");
        Assert.notNull(count, "Time类型的grouper需要指定count属性");
        return new TimeGrouper(timeRangeSize, count);
    }

    public ValueGrouper getValueGrouper() {
        Assert.notNull(valueRangeSize, "Value类型的grouper需要指定valueRangeSize属性");
        return new ValueGrouper(valueRangeSize);
    }

    public CustomGrouper getCustomGrouper() {
        //Assert.isTrue(StringUtils.isNotEmpty(customGrouperName), "Custom类型的grouper需要指定tag列表");
        Assert.isTrue(StringUtils.isNotEmpty(json), "Custom类型的grouper需要指定json属性");
        //return new CustomGrouper(customGrouperName, json);
        return new CustomGrouper("custom", json);
    }
}
