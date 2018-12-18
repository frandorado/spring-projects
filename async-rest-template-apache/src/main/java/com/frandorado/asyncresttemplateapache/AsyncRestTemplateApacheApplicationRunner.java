package com.frandorado.asyncresttemplateapache;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log
public class AsyncRestTemplateApacheApplicationRunner implements ApplicationRunner {
    
    private final AsyncRestTemplate apacheAsyncRestTemplate;
    private final AsyncRestTemplate simpleClientAsyncRestTemplate;
    
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("===== Execution without host header =====");
        log.info(execute(true, simpleClientAsyncRestTemplate));
        log.info(execute(true, apacheAsyncRestTemplate));
        
        log.info("===== Execution with host header =====");
        log.info(execute(false, simpleClientAsyncRestTemplate));
        log.info(execute(false, apacheAsyncRestTemplate));
    }
    
    private String execute(boolean withSuccess, AsyncRestTemplate asyncRestTemplate) {
        
        try {
            RequestEntity<String> requestEntity = createRequestEntity(withSuccess);
            Class<String> responseType = String.class;
            ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate.exchange(requestEntity.getUrl(), requestEntity.getMethod(),
                    requestEntity, responseType);
            ResponseEntity<String> entity = future.get();
            
            return String.format("Factory=[%s] ResponseBody=[%s]", asyncRestTemplate.getAsyncRequestFactory().getClass().getSimpleName(),
                    entity.getBody());
        } catch (InterruptedException | ExecutionException e) {
            return String.format("Factory=[%s] Exception=[%s]", asyncRestTemplate.getAsyncRequestFactory().getClass().getSimpleName(),
                    e.getMessage());
        } catch (URISyntaxException e) {
            return null;
        }
    }
    
    private RequestEntity createRequestEntity(boolean withSuccess) throws URISyntaxException {
        String url = "https://httpstat.us/200";
        HttpMethod method = HttpMethod.GET;
        HttpHeaders headers = new HttpHeaders();
        
        if (!withSuccess) {
            headers.add("host", "localhost:8080");
        }
        
        return new RequestEntity<>(headers, method, new URI(url));
    }
    
}
