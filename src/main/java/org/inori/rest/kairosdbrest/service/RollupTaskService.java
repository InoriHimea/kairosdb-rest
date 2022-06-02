package org.inori.rest.kairosdbrest.service;

import org.inori.rest.kairosdbrest.model.RollupParam;
import org.kairosdb.client.builder.RollupTask;

import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/1 13:25 6月
 * @since 1.8
 */
public interface RollupTaskService {

    RollupTask getTask(String taskId);

    List<RollupTask> listTask();

    RollupTask createTask(RollupParam rollupParam);

    boolean deleteTask(String taskId);
}
