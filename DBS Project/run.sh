echo "Running server."
java -classpath ./bin -Djava.rmi.server.hostname=192.168.1.2 main.backup.Server 1.0 1 PEER1 224.0.0.1 8001  224.0.0.2 8002  224.0.0.3 8003
