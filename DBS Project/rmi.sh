#!/bin/bash
echo "Starting rmiregistry."

compDir=./bin
mkdir -p $compDir

cd ./bin
rmiregistry
