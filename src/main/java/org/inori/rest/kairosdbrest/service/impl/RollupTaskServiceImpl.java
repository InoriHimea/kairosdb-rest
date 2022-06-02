package org.inori.rest.kairosdbrest.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.inori.rest.kairosdbrest.model.RollupParam;
import org.inori.rest.kairosdbrest.service.BaseKairosService;
import org.inori.rest.kairosdbrest.service.RollupTaskService;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/1 13:25 6月
 * @since 1.8
 */
@Service
@Log4j2
public class RollupTaskServiceImpl extends BaseKairosService implements RollupTaskService {

    @Autowired
    HttpClient kairosClient;

    @Override
    public RollupTask getTask(String taskId) {
        log.info("查询{}的RollupTask", taskId);
        return kairosClient.getRollupTask(taskId);
    }

    @Override
    public List<RollupTask> listTask() {
        return kairosClient.getRollupTasks();
    }

    @Override
    public RollupTask createTask(RollupParam rollupParam) {
        String taskName = rollupParam.getTaskName();
        String relativeTime = rollupParam.getRelativeTime();
        String saveAs = rollupParam.getSaveAs();

        Assert.isTrue(StringUtils.isNotBlank(taskName), "任务名称不能为空");
        Assert.isTrue(StringUtils.isNotBlank(relativeTime), "执行周期不能为空");
        Assert.isTrue(StringUtils.isNotBlank(saveAs), "新指标名不能为空");

        Map<String, Object> relativeTimeMap = new LinkedHashMap<>();
        Assert.isTrue(Pattern.matches("(\\d)* (\\w)*", relativeTime), "startDuring时间格式为: 时间+空格+单位，如(1 Hours)");
        this.parseDuring(relativeTime, relativeTimeMap);

        if (MapUtils.isNotEmpty(relativeTimeMap)) {
            RelativeTime time = new RelativeTime((Integer) relativeTimeMap.get("value"), (TimeUnit) relativeTimeMap.get("unit"));
            RollupBuilder rollupBuilder = RollupBuilder.getInstance(taskName, time);

            Rollup rollup = rollupBuilder.addRollup(saveAs);
            queryParam2QueryBuilder(rollupParam.getQueryParam(), rollup.addQuery());

            return kairosClient.createRollupTask(rollupBuilder);
        } else {
            log.warn("时间周期处理异常");
            return null;
        }
    }

    @Override
    public boolean deleteTask(String taskId) {
        log.info("删除{}的RollupTask", taskId);

        try {
            kairosClient.deleteRollupTask(taskId);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("删除任务异常: {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }
}
