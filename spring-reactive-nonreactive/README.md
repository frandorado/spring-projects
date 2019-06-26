# Spring Reactive vs Non-Reactive

Link to [Reactive vs Non-Reactive Spring Performance](https://frandorado.github.io/2019/06/26/spring-reactive-vs-non-reactive-performance.html) in blog

## How to test

1. Run docker-compose file to start MongoDB (/src/main/resources/docker-compose.yml)
    ```
    docker-compose up
    ```
2. Start the Spring Application
3. Modify the JMeter file (/src/main/resources/reactivevsnonreactive.jmx):
    * Set the endpoint for POST and GET (mvcsync | mvcasync | reactive)
    * Modify the number of users/threads
4. Run JMeter file from console
    ```
    jmeter -n -t reactivevsnonreactive.jmx -l result.jtl
    ```

