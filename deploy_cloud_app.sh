#!/bin/sh

cd ./editnow-core/
mvn clean install
cf push
cd ../editnow-scripts/
cf push
