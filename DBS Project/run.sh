#!/bin/bash

java -classpath bin -Djava.rmi.server.codebase=file:bin/ main.backup.Server 1.0 1 localhost  224.0.0.1 8001  224.0.0.2 8002  224.0.0.3 8003 && fg


