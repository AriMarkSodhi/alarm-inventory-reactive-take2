# alarminventory-reactive

take 2 adds more robustness around reactive approach and a resource microservice - mostly stubbed out for
now. 

The intent of this exercise is to use Spring boot to explore exposing an alarm model; alarm definitions,
alarm instances and the resources that they are raised by via a reactive REST API. Resource represent
device equipment and facilities. They have a containment model. These equipment and facilities represent 
amongst other things a device containment model - NE - rack, shelf, slot, port, power supply, sensor. These 
all have state, one of which is alarm state. A resource can be in an alarm state with a given severity.

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
  - reactive mongo -  Spring repository using reactive template delegation vs. reactive repository inheritance
  - Mongo docker image build in this project from latest mongo image via maven
- Nginx  - front ending the REST APIs acting as an API GW to proxy and load balance REST requests to microservices.
  - load balance/Proxy
  - rate limit - Ratelimiters using different strategies.
       - per client Request Rate limit - max num requests per time interval (only one supported today) - fixed, sliding
          - request/sec
          - request/min
          - request/hr
          - request/day
       - Concurrent Rate limit - max num concurrent requests at same time
       - Priority Based Rate limit - portion of bw reserved for hi-priority
       - utilization based rate limit - final type to prevent overload - based on priority - critical, posts, gets, tests
   - nginx image built in this project from latest nginx image via maven 

- Docker - importing, building and activating/deactivating images via docker and docker-compose. This is ok
  for experimentation on a single host, but a container orchestration engine like kubernetes is needed for
  dynamically scaling  production deployments. Assumed to be pre-installed. 
- Swagger/OpenAPI - embed documentation
- Traceability, Profiling using AOP

The application has four docker images all built via this project:
- the alarm REST API
- the resource REST API
- Nginx
- Mongodb
and a test client. Right now the application bootstraps the database. Only the resources, alarm definitions
and the alarm instances are modeled. The resources are in a separate REST controller and docker image.

Nginx will act as an API gateway for one or more services behind it. Each service has its own
database, but share a common mongodb server for now.

To run this system - using docker-compose 

    docker-compose -f docker-compose.yml

to retrieve alarms/resources

   curl -i -X GET "http://localhost:8090/alarminventory/alarmdefinitions"
   curl -i -X GET "http://localhost:8090/alarminventory/alarms"
   curl -i -X GET "http://localhost:80/resourceinventory/resources"

to retrieve by id

   curl -i -X GET "http://localhost:80/alarminventory/alarmdefinition/5c87f32f190494000187b566"
   curl -i -X GET "http://localhost:80/alarminventory/alarm/Alarm_1"
   curl -i -X GET "http://localhost:80/resourceinventory/resource/0"

to retrieve by name

   curl -i -X GET "http://localhost:80/alarminventory/alarmdefinition?name=Alarm_0"
   curl -i -X GET "http://localhost:80/alarminventory/alarm?name=Alarm_0"
   curl -i -X GET "http://localhost:80/resourceinventory/resource?name=res1"

