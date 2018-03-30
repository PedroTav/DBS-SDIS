#!/bin/bash

echo "Compilling project."

compDir=./bin/main/backup
mkdir -p $compDir
javac -d ./bin Backup.java Server.java Client.java MessageHeader.java
