#!/bin/bash
echo "Client Backup."
java -classpath bin main.backup.Client PEER1 BACKUP "test.jpg" 3
echo "Backup completed."
