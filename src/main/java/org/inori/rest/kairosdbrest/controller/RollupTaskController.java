package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.annotation.ApiDoc;
import org.inori.rest.kairosdbrest.model.RollupParam;
import org.inori.rest.kairosdbrest.service.RollupTaskService;
import org.kairosdb.client.builder.RollupTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/6/1 12:00 6月
 * @since 1.8
 */
@RestController
@Log4j2
@RequestMapping("/rollup")
@ApiDoc
public class RollupTaskController {

    @Autowired
    private RollupTaskService rollupTaskService;

    @GetMapping("/list")
    public List<RollupTask> listTask() {
        return rollupTaskService.listTask();
    }

    @GetMapping("/{id}")
    public RollupTask getTask(@PathVariable("id") String taskId) {
        return rollupTaskService.getTask(taskId);
    }

    @PostMapping("/create")
    public RollupTask createTask(RollupParam rollupParam) {
        return rollupTaskService.createTask(rollupParam);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTask(@PathVariable("id") String taskId) {
        return rollupTaskService.deleteTask(taskId);
    }
}
