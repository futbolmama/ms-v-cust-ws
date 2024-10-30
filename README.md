# Overview

Scratch project to manipulate Customer data in a postgres database.

# Prerequisites

* JDK 21 -- [SDKMAN!](https://sdkman.io/) is a great tool for managing JVM tools, like the JDK
* Maven -- [SDKMAN!](https://sdkman.io/) is a great tool for managing JVM tools, like the Maven
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running. Make sure to log into to gain
  access to all features.

1. API Endpoints

# Building

Execute `mvn --batch-mode --update-snapshots --errors clean verify` to assemble the application, run the automated tests
and create a container image.

## Running Using Maven & Docker Compose

Execute `mvn --batch-mode --update-snapshots --errors --projects application clean verify spring-boot:run` to build and
launch the application via Maven, using Docker Compose to launch downstream containers.

## Testing
curl -X GET http://localhost:8080/v1/api/customer/all
curl -X GET http://localhost:8080/v1/api/customer/1
curl -X PUT -H "Content-Type: application/json" -d '{"id": 1, "fName": "Linda", "mName": "", "lName": "Suarez", "email": "me2@o.co", "phone": "111-333-4444"}' http://localhost:8080/v1/api/customer/1
curl -X GET http://localhost:8080/v1/api/customer/1
curl -X PUT -H "Content-Type: application/json" -d '{"id": 1, "fName": "Linda", "mName": "", "lName": "Suarez", "email": "me2@o.co", "phone": "111-333-4444"}' http://localhost:8080/v1/api/customer/1
curl -X GET http://localhost:8080/v1/api/customer/2


### Connecting to the postgres database defined in docker-compose.yml
Execute `docker exec -it <container_id> psql -U testuser -d testdb`
Execute `\dt` to see the tables
Generate SQL to query


* username - testdb
* password - testuser
* database - testpass

You can also connect to the db using your IDE `jdbc:postgresql://localhost:60275/testdb`
Find the port number by doing a `docker ps -a`

# Troubleshooting

* Docker Desktop is a required for TestContainers
* The postgres container from a previous run is lingering
  * `docker stop <container_id>`
  * `docker rm <container_id>`

# References

* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/) - manages the assembly and launching of
  the application
* [Testcontainers](https://www.testcontainers.org/) - manages the creation and destruction of the containers during
  testing