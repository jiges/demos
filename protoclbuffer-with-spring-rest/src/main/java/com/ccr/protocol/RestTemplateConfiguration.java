package com.ccr.protocol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * @author ccr12312@163.com at 2019-4-24
 */
@Configuration
public class RestTemplateConfiguration {
    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }
    @Bean
    ProtobufHttpMessageConverter hmc() {
        return new ProtobufHttpMessageConverter();
    }
}
