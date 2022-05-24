package org.inori.rest.kairosdbrest.service.impl;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.service.DatapointService;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 15:36 5月
 * @since 1.8
 */
@Service
@Log4j2
public class DatapointServiceImpl implements DatapointService {

    @Autowired
    private HttpClient kairosClient;

    @Override
    public void query() {
        QueryBuilder queryBuilder = QueryBuilder.getInstance();
        queryBuilder.addMetric("");

    }
}
