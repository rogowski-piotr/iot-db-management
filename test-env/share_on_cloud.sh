#!/bin/sh

nohup ssh -o ServerAliveInterval=60 -o ExitOnForwardFailure=yes -nNT -R 8080:127.0.0.1:8080 azureuser@rogowski.codli.pl &
nohup ssh -o ServerAliveInterval=60 -o ExitOnForwardFailure=yes -nNT -R 8081:127.0.0.1:80 azureuser@rogowski.codli.pl &