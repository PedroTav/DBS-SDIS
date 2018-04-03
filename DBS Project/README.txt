Projeto: Distributed Backup Service

Grupo:8
Turma:3MIEIC03 

-- README --

-- LINUX --
Para compilar o projecto bast executar o script "comp.sh"
Para começar o peer primeiro é preciso executar o script "rmi.sh" para começar o rmiregistry e depois basta executar o script "run.sh" para inciar o peer "PEER1"

Para executar o cliente:

Para testar o protocolo de BACKUP pode ser executado o script "test.sh".

Para todos os outros:
java -classpath bin -Djava.rmi.server.hostname=<rmiHost> main.backup.Server <peer_ap> <sub_protocol> <opnd_1> <opnd_2>

-- WINDOWS --

Para executar o nosso projeto basta usar o script "make" para compilar os ficheiros e iniciar os peers ('PEER1','PEER2','PEER3').

Invocar o cliente é feito da seguinte forma: 

java main.backup.Client <peer_ap> <sub_protocol> <opnd_1> <opnd_2> 


-- Argumentos do cliente --

<rmiHost>: Endereço em o rmiregistry foi iniciado. ex:192.168.1.2

<peer_ap>: Determina qual dos peers vamos aceder. ex: PEER1

<sub_protocol>: Determina que tipo de operação vai ser executada. ex: RESTORE

<opnd_1>: Dependendo do protocolo, este operando representa o caminho para um ficheiro. ex: No caso do BACKUP

<opnd_2>: Este operando é necessário na operação BACKUP para determinar replication degree. 


