# DDD E-Commerce
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ebd3f4bb57384e5b9813b4aa05f3fc4c)](https://www.codacy.com/app/AdamOlszewskiIT/ddd-order-layers?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AdamOlszewskiIT/ddd-order-layers&amp;utm_campaign=Badge_Grade)

## Introduction

This project shows DDD approach to build e-commerce application with the the layered architecture. Application is divided into three main bounded contexts, i.e. 
*   **Sales**,
*   **Shipping**,
*   **CRM**.

...
## Architecture
![Layered architecture](./resources/layered.png?raw=true)

Each bounded context is divided into four layers which are shown on the picture above, i.e.
*   **Client**,
*   **Application**,
*   **Domain**,
*   **Infrastructure**.

### Client
Client layer allows to communicate with application in CQRS style. To change state of application Client must receive valid command object.

To read state of application Client must receive a valid query object.

### Application
Application layer orchestrate objects from different domains to accomplish certain user story.

### Domain
Domain is the core of the application and manage business logic. Domain is fully independent of framework so it can be moved to another project without code refactor.

### Infrastructure
Infrastructure provide tools, repositories, queues etc. for other layers. This is achieved by port-adapter pattern where all other layers communicate with others by ports (interfaces) and infrastructure provide adapter (implementation) for each port.
Ports can be divided into inner and outer ports, where former are implemented in domains an latter are implemented in infrastructure.

## Technology stack
*   **JAVA 11**
*   **SPRING BOOT 2.x**
*   **RABBITMQ**
*   **DOCKER**
*   **DOCKER-COMPOSE**

## Building and running application
To build and run this application go to root folder and run in console  

    gradle build bootRun

## Building  and running with docker
To build this application to image with docker run in console

    docker build -t ddd-layers .

and now you can run this image by

    docker run -d -p 8067:8067 ddd-layers
    
## Building and running with docker-compose
To build this application to docker image and run it with docker-compose run in console

    docker-compose up -d --build

### Project based on https://github.com/BottegaIT/ddd-leaven-v2
