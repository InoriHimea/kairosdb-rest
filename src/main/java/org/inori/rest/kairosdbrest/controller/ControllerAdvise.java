package org.inori.rest.kairosdbrest.controller;

import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 17:05 5月
 * @since 1.8
 */
@RestControllerAdvice
@Log4j2
public class ControllerAdvise {

    /**
     * 异常返回异常结构
     * @param t 异常的对象
     * @return 异常的json信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<String> throwableHandler(Throwable t) {
        log.error("请求异常: {}", t.getMessage(), t);
        return Mono.just(JsonUtils.toJson(t));
    }
}