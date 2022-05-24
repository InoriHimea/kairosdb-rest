package org.inori.rest.kairosdbrest.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;

import java.net.URISyntaxException;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/24 13:36 5月
 * @since 1.8
 */
@Configuration
@EnableWebFluxSecurity
@Log4j2
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws URISyntaxException {
        log.info("WebFlux Security begin");
        return http.authorizeExchange()
                .pathMatchers(HttpMethod.POST)
                .authenticated()
                .pathMatchers(HttpMethod.DELETE)
                .authenticated()
                .pathMatchers(HttpMethod.PUT)
                .authenticated()
                .pathMatchers("/", "/swagger-ui.html")
                .authenticated()
                .anyExchange()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .and()
                .httpBasic()
                .authenticationEntryPoint(new HttpBasicServerAuthenticationEntryPoint())
                .and()
                .logout()
                .and()
                .build();
    }
}
