#!/bin/bash

echo "Source env"
source env_chaincode


peer channel getinfo -o ${ORDERER_ADDR} -c ${CHANNEL}  --tls --cafile ${ORDERER_CERT}
#2024-03-07 14:02:14.788 UTC [channelCmd] InitCmdFactory -> INFO 001 Endorser and orderer connections initialized
#Blockchain info: {"height":1,"currentBlockHash":"ItXIYzNfrfIn2Vhf4YcwP9ANUu1VxbrJEynuw+Pka/I="}

peer channel list -o ${ORDERER_ADDR} --tls --cafile ${ORDERER_CERT}



#Join network
echo "Create channel ${CHANNEL}"
peer channel create -o ${ORDERER_ADDR} -c ${CHANNEL} -f /etc/hyperledger/config/${CHANNEL}.tx --tls --cafile ${ORDERER_CERT}
echo "Join channel ${CHANNEL}"
peer channel join -b ${CHANNEL}.block
