# car-rental-microservices

## Pre-requisites
- Docker 
- IntelliJ

## Getting started

### Clone the repository
```
git clone https://github.com/caf3babe/car-rental-microservices.git
```

### Start the docker-compose.yaml 
```
cd docker-compose 
docker-compose up
```

### Start the services supplying the main functionality
To provide all functionality these 7 modules need to be running in IntelliJ
* api-gateway
* authentication-service
* car-service
* currency-service
* location-service
* opening-hours-service
* order-service

### Reaching the webservice
All services are available by the default under [localhost:8079/api/v1]()

The available endpoints for each service are documented with Swagger under the corresponding service port. e.g., ``locahost:8080/swagger-ui/index.html`` for the currency service.

### Reaching Prometheus
To use and view the metrics gathered Prometheus, supply your private IP address under ``docker-compose/prometheus/prometheus.yml``

```
- targets: [ 'YOUR-IP:PORT' ]
```
