start javac -d . Backup.java Server.java Client.java MessageHeader.java
start rmiregistry
start java -classpath . -Djava.rmi.server.codebase=file:./ main.backup.Server
pause