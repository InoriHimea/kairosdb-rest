package org.inori.rest.kairosdbrest.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.inori.rest.kairosdbrest.annotation.ApiDoc;
import org.inori.rest.kairosdbrest.enums.SwaggerDocType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.*;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 16:07 5月
 * @since 1.8
 */
@Configuration
@EnableOpenApi
@EnableSwagger2
@ConfigurationProperties("api.swagger")
@Log4j2
public class SwaggerConfig {

    private static final List<Response> RESPONSE_LIST = new LinkedList<>();

    @Setter
    @Getter
    private boolean enable = false;
    @Setter
    @Getter
    private SwaggerDocType type = SwaggerDocType.OAS_30;

    @PostConstruct
    public void init() {
        RESPONSE_LIST.addAll(Stream.of(
                        new ResponseBuilder()
                                .code(String.valueOf(OK.value()))
                                .isDefault(true)
                                .description("陈工")
                                .build(),
                        new ResponseBuilder()
                                .code(String.valueOf(NOT_FOUND.value()))
                                .description("未找到")
                                .build(),
                        new ResponseBuilder()
                                .code(String.valueOf(FORBIDDEN.value()))
                                .description("精致访问")
                                .build(),
                        new ResponseBuilder()
                                .code(String.valueOf(UNAUTHORIZED.value()))
                                .description("无权限")
                                .build(),
                        new ResponseBuilder()
                                .code(String.valueOf(CREATED.value()))
                                .description("创建支援")
                                .build(),
                        new ResponseBuilder()
                                .code(String.valueOf(NO_CONTENT.value()))
                                .description("无内容")
                                .build(),
                        new ResponseBuilder()
                                .code(String.valueOf(INTERNAL_SERVER_ERROR.value()))
                                .description("服务异常")
                                .build())
                .collect(Collectors.toList())
        );
    }

    @Bean
    public Docket docket() {
        return new Docket(new DocumentationType(type.getName(), type.getVersion()))
                .apiDescriptionOrdering(Comparator.comparing(ApiDescription::getPath))
                .enable(enable)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                .forCodeGeneration(true)
                .groupName("KairosdbServer")
                .globalResponses(HttpMethod.GET, RESPONSE_LIST)
                .globalResponses(HttpMethod.POST, RESPONSE_LIST)
                .globalResponses(HttpMethod.PUT, RESPONSE_LIST)
                .globalResponses(HttpMethod.DELETE, RESPONSE_LIST)
                .pathMapping("/")
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(ApiDoc.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("KairosDB的接口文档")
                .version("1.0")
                .description("Restful API For Kairosdb")
                .contact(new Contact("InoriHimea", "https://qx.inori0114.ml", "inori-admin@inori0114.ml"))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/license/LICENSE-2.0.html")
                .termsOfServiceUrl("https://qx.inori0114.ml:36000").build();
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization",
                        new AuthorizationScope[] {new AuthorizationScope("global", "RestAuth")})))
                .operationSelector(httpMethod ->
                        httpMethod.httpMethod() == HttpMethod.POST || httpMethod.httpMethod() == HttpMethod.PUT || httpMethod.httpMethod() == HttpMethod.DELETE)
                .build());
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new BasicAuth("Authorization"));
    }
}
