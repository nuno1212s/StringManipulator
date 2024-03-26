# string-processor
A simple string manipulation using an Event Driven Architecture.

## REST End points
The service is available at port :8080.

### POST /api/v1/strings
This end point is used to publish a string into the system. This string will then be processed by the system and the result will be published to STDOUT.

Body:
```json
{
  "inputStr": "YOUR STRING HERE"
}
```
#### Responds with
200 OK - If the string was successfully published to the system.

400 Bad Request - If the input is malformed.
# How to run

## Prerequisites
[Docker](https://docs.docker.com/engine/install/) and docker-compose should be installed on your machine.

You should be running some form of UNIX, with support for bash scripts.

## Running the application

```
./run.sh
```

This will launch the docker container for Artemis as well as build and launch the image for the String Processor.