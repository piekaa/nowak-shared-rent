#!/usr/bin/env bash

mvn clean install
cd ./target/
scp nowak-shared-rent-0.0.1-SNAPSHOT.jar jackie@piekoszek.pl:/home/jackie/
ssh jackie@piekoszek.pl 'bash /home/jackie/start-app-piekoszek.sh'