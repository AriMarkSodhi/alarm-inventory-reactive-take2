The intent of this exercise is to use Spring boot to explore exposing an alarm model; alarm definitions,
alarm instances and the resources that they are raised by via a reactive REST API. Resource represent
device equipment and facilities. These equipment and facilities represent amongst other things
a device containment model - NE - rack, shelf, slot, port, power supply, sensor. These all have state,
one of which is alarm state. A resource can be in an alarm state with a given severity.

The alarm definitions will be effectively r/o via the REST API as they are discovered from resources
that define them and publish instances of them. The alarm instances are published by resources but can
be manipulated via the REST API.

The intent is to link the resources, alarm definitions, and alarm instances via a REST
API following HATEOAS principles. This will enable an alarm client to effectively navigate
and discover the model at run time.


The technology being explored includes:
- Spring Boot DI and AOP
  - Reactive Alarm Controller (this should be two separate APIs - one for Alarm Definition
  and one for alarm instances) and a SpringApplication supporting them.
  - Reactive inventory controller.  Reactive controllers utilize streaming APIs to improve concurrency.
    - Flux - a stream of zero or more objects
    - Mono - a stream of zero or one object
  - Spring Annotations for REST APIs
  - Spring Security
    - authentication and authorization via oauth
    - mitigate OWASP issues
  - Spring test client
    - unit and integration tests
- Mongodb
  - Spring annotations for Mongo persistent collections or alarms, alarm definitions and resources.
  - reactive mongo -  Spring repository using template delegation vs. repository inheritance
  - Mongo docker image
- Nginx  - front ending the REST APIs acting as an API GW
  - load balance
  - rate limit - Ratelimiters using different strategies.
       - per client Request Rate limit - max num requests per time interval - fixed, sliding
          - request/sec
          - request/min
          - request/hr
          - request/day
       - Concurrent Rate limit - max num concurrent requests at same time
       - Priority Based Rate limit - portion of bw reserved for hi-priority
       - utilization based rate limit - final type to prevent overload - based on priority - critical, posts, gets, tests

- Docker - importing, building and activating/deactivating images via docker and docker-compose. This is ok
  for experimentation on a single host, but a container orchestration engine like kubernetes is needed for
  dynamically scaling  production deployments.
- Swagger/OpenAPI - embed documentation
- Traceability, Profiling using AOP

The application has three docker images:
- the alarm REST API
- Nginx
- Mongodb
and a test client. Right now the application bootstraps the database. Only the alarm definitions
and the alarm instances are modeled. The resources will be a separate REST controller and
docker image.

Nginx will act as an API gateway for one or more services behind it. Each service has its own
database, but share a common mongodb server for now.

There are two ways to run this system - using docker-compose or docker directly.

Run mongo db in docker image

    docker run --rm --network=ms-exp-bridge -p=27017:27017 --name=alarm-inv-mongo

Run docker image with alarm inv app


Or use docker-compose

    docker-compose -f docker-compose.yml


to retrieve alarms

   curl -i -X GET "http://localhost:8090/alarminventory/alarmdefinitions"
   curl -i -X GET "http://localhost:8090/alarminventory/alarms"


Run mongo db in docker image

    docker run --rm --network=ms-exp-bridge -p=27017:27017 --name=alarm-inv-mongo

Run docker image with alarm inv app

