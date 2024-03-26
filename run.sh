#!/bin/bash

./mvnw package

docker compose up -d --build
