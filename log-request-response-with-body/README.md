# Logging Request and Response with body in Spring.

This project shows a way to log requests and responses in Spring including the body data. More info [here]()

## How to test

Start the Spring Application
 
### GET Request

Send a GET request `http://localhost:8080/greetings/12`

* Response:
    ```
    {
        "id": 12,
        "message": "Hello world!"
    }
    ```
* Logs:
    ```
    2018-10-27 16:04:14.302  INFO 88149 --- [nio-8080-exec-2] c.f.l.service.LoggingServiceImpl         : REQUEST method=[GET] path=[/greetings/12] headers=[{cookie=JSESSIONID=lqPKUYOnoisYTVVqsDxsmZWszZ0W_pCfobhBSut0, postman-token=0d2ab740-1745-457e-be51-ad4c171422b8, host=localhost:8080, connection=keep-alive, cache-control=no-cache, accept-encoding=gzip, deflate, user-agent=PostmanRuntime/7.3.0, accept=*/*}] 
    2018-10-27 16:04:14.383  INFO 88149 --- [nio-8080-exec-2] c.f.l.service.LoggingServiceImpl         : RESPONSE method=[GET] path=[/greetings/12] responseHeaders=[{}] responseBody=[GreetingResponse(id=12, message=Hello world!)] 
    ```

### POST Request

Send a POST request `http://localhost:8080/greetings`

* Request body:
    ```
    {
        "message": "Hello Java world!"
    }
    ```
* Response body:
    ```
    {
        "id": "1",
        "message": "Hello Java world!"
    }
    ```
* Logs:
    ```
    2018-10-27 16:17:28.913  INFO 88149 --- [nio-8080-exec-5] c.f.l.service.LoggingServiceImpl         : REQUEST method=[POST] path=[/greetings] headers=[{content-length=38, cookie=JSESSIONID=lqPKUYOnoisYTVVqsDxsmZWszZ0W_pCfobhBSut0, postman-token=d3957f0e-4dd7-4ae0-aaca-44febc487a22, host=localhost:8080, content-type=application/json, connection=keep-alive, cache-control=no-cache, accept-encoding=gzip, deflate, user-agent=PostmanRuntime/7.3.0, accept=*/*}] body=[GreetingRequest(message=Hello Java world!)]
    2018-10-27 16:17:28.918  INFO 88149 --- [nio-8080-exec-5] c.f.l.service.LoggingServiceImpl         : RESPONSE method=[POST] path=[/greetings] responseHeaders=[{}] responseBody=[GreetingResponse(id=1, message=Hello Java world!)] 
    ```
