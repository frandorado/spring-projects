package com.frandorado.springcustomserializerdeserializer.bootstrap;

import static com.frandorado.springcustomserializerdeserializer.SpringCustomSerializerDeserializerApplication.CUSTOM_API;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class RequestsHandlerInterceptorAdapterConfig extends HandlerInterceptorAdapter {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(CUSTOM_API, request.getHeader(CUSTOM_API));
        return true;
    }
}
