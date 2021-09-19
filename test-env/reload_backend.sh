#!/bin/sh

docker exec -it client-api ./reload.sh client-api-0.0.1-SNAPSHOT.jar
docker exec -it sensor-managment ./reload.sh sensor-managment-0.0.1-SNAPSHOT.jar