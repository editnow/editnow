#!/bin/sh

docker run -d --hostname editnow-rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3-management
