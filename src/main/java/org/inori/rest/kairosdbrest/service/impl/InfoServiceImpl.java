package org.inori.rest.kairosdbrest.service.impl;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.service.InfoService;
import org.inori.rest.kairosdbrest.utils.TimeUtil;
import org.kairosdb.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 17:37 5月
 * @since 1.8
 */
@Service
@Log4j2
public class InfoServiceImpl implements InfoService {

    @Autowired
    private HttpClient kairosClient;

    @Override
    public Boolean status() {
        return kairosClient.getStatusCheck() == 500 ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public List<String> listStatus() {
        log.info("查询kairos服务的状态: {}", TimeUtil.getFormatNowTime());
        return kairosClient.getStatus();
    }

    @Override
    public int checkStatus() {
        log.info("查询kairos服务的状态码: {}", TimeUtil.getFormatNowTime());
        return kairosClient.getStatusCheck();
    }

    @Override
    public String version() {
        log.info("查询kairos服务的版本: {}", TimeUtil.getFormatNowTime());
        return kairosClient.getVersion();
    }
}
