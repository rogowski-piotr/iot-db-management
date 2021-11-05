#!/bin/sh

APP_FILE=$1
PID_FILE="${APP_FILE%.*}.pid"
LOG_FILE="${APP_FILE%.*}.log"

if [ -f $PID_FILE ]; then
        cat $PID_FILE | xargs kill -9
fi

nohup java -jar $APP_FILE > $LOG_FILE &
rm -f $PID_FILE
echo "$!" > $PID_FILE
