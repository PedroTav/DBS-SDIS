start javac -d . Backup.java Server.java Client.java Message.java
start rmiregistry
start java main.backup.Server 1.0 1 BOT1 224.0.0.1 8001  224.0.0.2 8002  224.0.0.3 8003
pause

