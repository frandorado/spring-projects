# Spring Reactive vs Non-Reactive

Link to [Spring Batch AWS Series](https://frandorado.github.io/spring/2019/07/29/spring-batch-aws-series-introduction.html)


## How to test

More info in 

* Run the docker-compose.yml file
  * `docker-compose up`
  * `TMPDIR=/private$TMPDIR docker-compose up` (MAC users)

* Create the queues if don't exist

  ```
  aws sqs create-queue --endpoint http://localhost:4576 --queue-name step1-request.fifo --attributes '{"FifoQueue": "true", "ContentBasedDeduplication":"true"}'

  aws sqs create-queue --endpoint http://localhost:4576 --queue-name step1-response.fifo --attributes '{"FifoQueue": "true", "ContentBasedDeduplication":"true"}'

  ```

* Run one or more slaves using the main class `com.frandorado.springbatchawsintegrationslave.SpringBatchAwsIntegrationSlaveApplication`

* Run the master using the main application `com.frandorado.springbatchawsintegrationmaster.SpringBatchAwsIntegrationMasterApplication`