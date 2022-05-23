package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.utils.TimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 15:54 5月
 * @since 1.8
 */
@RestController
@Log4j2
public class IndexController {

    @GetMapping("/")
    public Mono<String> index() {
        return Mono.just("Welcome! Restful Server For Kairosdb CURD: " + TimeUtil.getFormatNowTime());
    }
}
