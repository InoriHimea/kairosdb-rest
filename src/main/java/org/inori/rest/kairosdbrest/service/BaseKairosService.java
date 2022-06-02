package org.inori.rest.kairosdbrest.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.inori.rest.kairosdbrest.enums.AggregatorEnums;
import org.inori.rest.kairosdbrest.enums.GrouperEnums;
import org.inori.rest.kairosdbrest.model.*;
import org.inori.rest.kairosdbrest.utils.TimeUtil;
import org.kairosdb.client.builder.AbstractQueryBuilder;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.QueryMetric;
import org.kairosdb.client.builder.TimeUnit;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/1 14:09 6月
 * @since 1.8
 */
@Log4j2
public class BaseKairosService {

    private static final String KAIROS_START_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String KAIROS_DATE_TIME_CHECK_PATTERN = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})";

    protected QueryBuilder queryParam2QueryBuilder(QueryParam queryParam) {
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        queryParam2QueryBuilder(queryParam, queryBuilder);
        return queryBuilder;
    }

    protected void queryParam2QueryBuilder(QueryParam queryParam, QueryBuilder queryBuilder) {
        checkRequiredParam(queryParam, queryBuilder);

        String metricName = queryParam.getMetricName();
        QueryMetric queryMetric = queryBuilder.addMetric(metricName);

        QueryMetric.Order order = queryParam.getOrder();
        if (order != null) {
            queryMetric.setOrder(order);
        }

        Integer limit = queryParam.getLimit();
        if (limit != null) {
            queryMetric.setLimit(limit);
        }

        Map<String, String> tags = queryParam.getTags();
        if (MapUtils.isNotEmpty(tags)) {
            queryMetric.addTags(tags);
        }

        Map<String, List<String>> multiValueTags = queryParam.getMultiValueTags();
        if (MapUtils.isNotEmpty(multiValueTags)) {
            queryMetric.addMultiValuedTags(multiValueTags);
        }

        List<Aggregators> aggregators = queryParam.getAggregators();
        if (CollectionUtils.isNotEmpty(aggregators)) {
            aggregators.forEach(aggregator -> this.parseAggregator(queryMetric, aggregator));
        }

        List<Groupers> groupers = queryParam.getGroupers();
        if (CollectionUtils.isNotEmpty(groupers)) {
            groupers.forEach(grouper -> this.parseGroup(queryMetric, grouper));
        }
    }

    protected void checkRequiredParam(QueryParam queryParam, AbstractQueryBuilder<?> queryBuilder) {
        String metricName = queryParam.getMetricName();
        String startTime = queryParam.getStartTime();
        String endTime = queryParam.getEndTime();
        String startDuring = queryParam.getStartDuring();
        String endDuring = queryParam.getEndDuring();

        Assert.isTrue(StringUtils.isNotBlank(metricName), "指标名称不能为空");
        Assert.isTrue(StringUtils.isNotBlank(startTime) || StringUtils.isNotBlank(startDuring), "开始时间不能为空");

        LocalDateTime startDateTime = null;
        if (StringUtils.isNotBlank(startTime)) {
            Assert.isTrue(Pattern.matches(KAIROS_DATE_TIME_CHECK_PATTERN, startTime), "startTime时间格式必须是" + KAIROS_START_TIME_PATTERN);
            startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(KAIROS_START_TIME_PATTERN));
        }

        Map<String, Object> startDuringMap = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(startDuring)) {
            Assert.isTrue(Pattern.matches("(\\d)* (\\w)*", startDuring), "startDuring时间格式为: 时间+空格+单位，如(1 Hours)");
            this.parseDuring(startDuring, startDuringMap);
        }

        LocalDateTime endDateTime = null;
        if (StringUtils.isNotBlank(endTime)) {
            Assert.isTrue(Pattern.matches(KAIROS_DATE_TIME_CHECK_PATTERN, endTime), "endTime时间格式必须是" + KAIROS_START_TIME_PATTERN);
            endDateTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(KAIROS_START_TIME_PATTERN));
        }

        Map<String, Object> endDuringMap = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(endDuring)) {
            Assert.isTrue(Pattern.matches("(\\d)* (\\w)*", endDuring), "endDuring: 时间+空格+单位，如(1 Hours)");
            this.parseDuring(endDuring, endDuringMap);
        }

        /*
            fixme: gson问题导致无法序列化
         */
        //queryBuilder.setTimeZone(TimeZone.getDefault());

        if (startDateTime != null)  {
            queryBuilder.setStart(TimeUtil.toDate(startDateTime))
                    .setEnd(endDateTime == null ? null : TimeUtil.toDate(endDateTime));
        }

        if (startDuring != null) {
            queryBuilder.setStart((Integer) startDuringMap.get("value"), (TimeUnit) startDuringMap.get("unit"))
                    .setEnd((Integer) endDuringMap.get("value"), (TimeUnit) endDuringMap.get("unit"));
        }
    }

    protected void parseDuring(String duringStr, Map<String, Object> duringMap) {
        String[] duringArr = duringStr.split(" ");
        int value = Integer.parseInt(duringArr[0]);
        String unit = duringArr[1];

        duringMap.put("value", value);
        duringMap.put("unit", TimeUnit.valueOf(unit.toUpperCase()));
    }

    protected void parseAggregator(QueryMetric queryMetric, Aggregators aggregator) {
        AggregatorEnums aggregatorName = aggregator.getAggregatorName();
        log.info("给定的aggregatorName -> {}", aggregatorName);
        AggregatorWrapper aggregatorContent = aggregator.getGrouperContent();
        Assert.notNull(aggregatorContent, "tagGrouperContent需要有效内容");

        switch (aggregatorName) {
            case AVG:
            case COUNT:
            case FIRST:
            case GAPS:
            case LAST:
            case MAX:
            case MIN:
            case SUM:
            case LEAST_SQUARES:
                queryMetric.addAggregator(aggregatorContent.getSamplingAggregator(aggregatorName.getJsonValue()));
                break;
            case SAMPLER:
            case SAVE_AS:
            case TRIM:
            case SCALE:
            case FILTER:
            case DEV:
            case DIV:
            case DIFF:
                throw new UnsupportedOperationException("暂未做支持配置");
            case RATE:
                queryMetric.addAggregator(aggregatorContent.getRateAggregator());
                break;
            case PERCENTILE:
                queryMetric.addAggregator(aggregatorContent.getPercentileAggregator());
                break;
            case CUSTOM:
            default:
                queryMetric.addAggregator(aggregatorContent.getCustomAggregator());
                break;
        }
    }

    protected void parseGroup(QueryMetric queryMetric, Groupers groupers) {
        GrouperEnums groupName = groupers.getGrouperName();
        log.info("给定的groupName -> {}", groupName);
        GrouperWrapper grouperContent = groupers.getGrouperContent();
        Assert.notNull(grouperContent, "tagGrouperContent需要有效内容");

        switch (groupName) {
            case TAG:
                queryMetric.addGrouper(grouperContent.getTagGrouper());
                break;
            case BIN:
                queryMetric.addGrouper(grouperContent.getBinGrouper());
                break;
            case TIME:
                queryMetric.addGrouper(grouperContent.getTimeGrouper());
                break;
            case VALUE:
                queryMetric.addGrouper(grouperContent.getValueGrouper());
                break;
            case CUSTOM:
            default:
                queryMetric.addGrouper(grouperContent.getCustomGrouper());
                break;
        }
    }
}
