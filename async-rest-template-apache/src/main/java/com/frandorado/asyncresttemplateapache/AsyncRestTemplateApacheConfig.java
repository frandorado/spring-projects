package com.frandorado.asyncresttemplateapache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class AsyncRestTemplateApacheConfig {
    
    @Bean
    public AsyncRestTemplate apacheAsyncRestTemplate() {
        return new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());
    }
    
    @Bean
    public AsyncRestTemplate simpleClientAsyncRestTemplate() {
        return new AsyncRestTemplate();
    }
}
