#!/bin/sh

PGPASSWORD=postgres psql -h localhost -p 5432 -U root postgres -c "DROP SCHEMA sensors CASCADE"
PGPASSWORD=postgres psql -h localhost -p 5432 -U root postgres -c "CREATE SCHEMA sensors"

docker exec -it client-api ./reload.sh client-api-0.0.1-SNAPSHOT.jar
docker exec -it sensor-managment ./reload.sh sensor-managment-0.0.1-SNAPSHOT.jar