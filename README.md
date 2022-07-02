# console-chat
Simple Chatting Console written in java using socket on localhost

## Set Up

### Build

Before running the program, build using the following command:

```bash
bash run.sh build
```

or using the following commands if don't have bash (make sure to be in the root directory):

```powershell
cd src
javac -d ../out ChatClient.java ChatServer.java ReadThread.java UserThread.java WriteThread.java
```

### To Run

#### Running Server

First run the server to listen on specific `<port>`:  

```bash
bash run.sh server <port>
```

or using the following commands if don't have bash (make sure to be in the root directory):

```powershell
cd out
java ChatServer <port>
```

example: running server on port 9000

```bash
bash run.sh server 9000
```

#### Running Client

Run the client and include it to the same `<port>` as the server:  

```bash
bash run.sh client <port>
```

or using the following commands if don't have bash (make sure to be in the root directory):
> since we are using the same computer, so just put in localhost

```powershell
cd out
java ChatClient localhost <port>
```

example: running server on port 9000

```bash
bash run.sh client 9000
```

## Commands

the current program support two operations

1. `$bye`: client exit the program
2. `$change`: change the room

`$change` command requires another argument

`$change <room name>`

the message that being sent only broadcast for clients in the same room. 
by default the room name is set to `public`