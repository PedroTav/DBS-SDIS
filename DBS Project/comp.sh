#!/bin/bash
echo "Compilling project."
compDir=./bin
mkdir -p $compDir

chuncks=./fileChunks
mkdir -p $chuncks

restoreDir=./restoredFiles
mkdir -p $restoreDir

javac -d ./bin Backup.java Server.java Client.java Message.java
echo "Compilled project."
