package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.annotation.ApiDoc;
import org.inori.rest.kairosdbrest.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 17:02 5月
 * @since 1.8
 */
@RestController
@Log4j2
@RequestMapping("/info")
@ApiDoc
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/status")
    public Mono<Boolean> status() {
        return Mono.just(infoService.status())
                .onErrorReturn(Boolean.FALSE);
    }

    @GetMapping("/status/list")
    public Mono<List<String>> statusList() {
        return Mono.just(infoService.listStatus())
                .onErrorReturn(Collections.emptyList());
    }

    @GetMapping("/status/check")
    public Mono<Integer> statusCheck() {
        return Mono.just(infoService.checkStatus())
                .onErrorReturn(-1);
    }

    @GetMapping("/version")
    public Mono<String> version() {
        return Mono.just(infoService.version())
                .onErrorReturn("0.0.0");
    }
}
