package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.annotation.ApiDoc;
import org.inori.rest.kairosdbrest.model.QueryParam;
import org.inori.rest.kairosdbrest.service.DatapointService;
import org.kairosdb.client.response.QueryResult;
import org.kairosdb.client.response.TagQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 14:33 5月
 * @since 1.8
 */
@RestController
@Log4j2
@RequestMapping("/datapoint")
@ApiDoc
public class DatapointController {

    @Autowired
    private DatapointService datapointService;

    @PostMapping("/query")
    public List<QueryResult> query(@RequestBody QueryParam queryParam) {
        return datapointService.query(queryParam);
    }

    @PostMapping("/query/tags")
    public List<TagQueryResult> queryTags(@RequestBody QueryParam queryParam) {
        return datapointService.queryTags(queryParam);
    }

    @GetMapping("/list/metrics")
    public List<String> queryMetricsNames() {
        return datapointService.listMetricsName();
    }

    @PostMapping("/metric/exists")
    public Map<String, Boolean> checkMetricExists(@RequestBody List<String> metricsNameList) {
        return datapointService.checkMetricExists(metricsNameList);
    }

    @DeleteMapping("/delete/{metric}")
    public boolean deleteMetric(@PathVariable("metric") String metricName) {
        return datapointService.deleteMetric(metricName);
    }

    @PostMapping("/delete/points")
    public boolean deleteMetricPoints(@RequestBody QueryParam queryParam) {
        return datapointService.deleteMetricPoints(queryParam);
    }
}
