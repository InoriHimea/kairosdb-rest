package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.annotation.ApiDoc;
import org.inori.rest.kairosdbrest.model.QueryParam;
import org.inori.rest.kairosdbrest.service.DatapointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void query(@RequestBody QueryParam queryParam) {
        datapointService.query(queryParam);
    }
}
