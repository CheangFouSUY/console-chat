#!/bin/bash

operation=$1
port=$2

if [ "${operation}" == "build" ]
then
    cd src
    javac -d ../out ChatClient.java ChatServer.java ReadThread.java UserThread.java WriteThread.java
elif [ "${operation}" == "server" ]
then
    cd out
    java ChatServer "${port}"
else
    cd out
    java ChatClient localhost "${port}"
fi