package org.inori.rest.kairosdbrest.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2021/10/6 21:51
 * @since 1.8
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public enum SwaggerDocType {

    /**
     * 开启的Swagger的版本
     */

    SWAGGER_12("swagger", "1.2"),
    SWAGGER_2("swagger", "2.0"),
    OAS_30("openApi", "3.0"),
    @Deprecated
    SPRING_WEB("spring-web", "5.2");

    private final String name;
    private final String version;
}
