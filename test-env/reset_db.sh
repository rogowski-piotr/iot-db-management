#!/bin/sh

PGPASSWORD=postgres psql -h localhost -p 5432 -U root postgres -c "DROP SCHEMA sensors CASCADE"
PGPASSWORD=postgres psql -h localhost -p 5432 -U root postgres -c "CREATE SCHEMA sensors"
