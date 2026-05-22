package com.msa.stock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public feign.okhttp.OkHttpClient feignOkHttpClient() {
        return new feign.okhttp.OkHttpClient();
    }
}
