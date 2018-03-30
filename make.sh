!# /bin/bash

javac -d . Backup.java Server.java Client.java MessageHeader.java &
java -classpath . -Djava.rmi.server.codebase=file:./ main.backup.Server &
rmiregistry
