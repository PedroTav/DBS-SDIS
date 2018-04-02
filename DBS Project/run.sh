#!/bin/bash

echo "Running server."

java -classpath . -Djava.rmi.server.hostname=192.1.1.2 -Djava.rmi.server.codebase=file:./ main.backup.Server 1.0 1 192.168.1.2  224.0.0.1 8001  224.0.0.2 8002  224.0.0.3 8003 && fg


