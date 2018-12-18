# AsyncRestTemplate with HttpComponentsAsyncClientHttpRequestFactory (Apache)

Link to [entry](https://frandorado.github.io/spring/2018/12/17/asyncresttemplate-apache-404.html) in blog

## How to test

Start the Spring Application
 
* Logs:
    ```
     2018-12-17 20:10:00.250  INFO 34448 --- [nio-8080-exec-2] .f.a.c.AsyncRestTemplateApacheController : ===== Execution without host header =====
     2018-12-17 20:10:01.217  INFO 34448 --- [nio-8080-exec-2] .f.a.c.AsyncRestTemplateApacheController : Factory=[SimpleClientHttpRequestFactory] ResponseBody=["200 OK"]
     2018-12-17 20:10:02.065  INFO 34448 --- [nio-8080-exec-2] .f.a.c.AsyncRestTemplateApacheController : Factory=[HttpComponentsAsyncClientHttpRequestFactory] ResponseBody=["200 OK"]
     2018-12-17 20:10:02.065  INFO 34448 --- [nio-8080-exec-2] .f.a.c.AsyncRestTemplateApacheController : ===== Execution with host header =====
     2018-12-17 20:10:02.246  INFO 34448 --- [nio-8080-exec-2] .f.a.c.AsyncRestTemplateApacheController : Factory=[SimpleClientHttpRequestFactory] ResponseBody=["200 OK"]
     2018-12-17 20:10:02.426  WARN 34448 --- [nio-8080-exec-2] o.s.web.client.AsyncRestTemplate         : Async GET request for "https://httpstat.us/200" resulted in 404 (Site Not Found); invoking error handler
     2018-12-17 20:10:02.428  INFO 34448 --- [nio-8080-exec-2] .f.a.c.AsyncRestTemplateApacheController : Factory=[HttpComponentsAsyncClientHttpRequestFactory] Exception=[org.springframework.web.client.HttpClientErrorException: 404 Site Not Found]
    ```
