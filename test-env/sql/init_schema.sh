#!bin/bash

PGPASSWORD=postgres psql -U root postgres -c "CREATE SCHEMA sensors"