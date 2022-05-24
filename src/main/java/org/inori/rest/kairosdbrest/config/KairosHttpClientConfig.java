package org.inori.rest.kairosdbrest.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.kairosdb.client.HttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2022/5/23 17:45 5月
 * @since 1.8
 */
@Configuration
@ConfigurationProperties("kairos")
@Log4j2
public class KairosHttpClientConfig {

    @Getter
    @Setter
    private String domain = "127.0.0.1";
    @Getter
    @Setter
    private String schema = "http";
    @Getter
    @Setter
    private Integer port = 8090;

    @Bean("kairosClient")
    public HttpClient httpClient() throws MalformedURLException {
        return new HttpClient(HttpClientBuilder.create()
                .useSystemProperties()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .build(), this.schema + "://" + this.domain + ":" + this.port);
    }
}
