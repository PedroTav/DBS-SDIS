#!/bin/bash

java -classpath bin -Djava.rmi.server.codebase=file:bin/ main.backup.Server && fg


