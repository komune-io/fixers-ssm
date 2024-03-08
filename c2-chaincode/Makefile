VERSION = $(shell cat ../VERSION)
include ./.env_version
export

package: chaincode-ssm-package chaincode-ex02-package hfabric-sandbox-ca-package hfabric-sandbox-cli-package hfabric-sandbox-orderer-package hfabric-sandbox-peer-package hfabric-sandbox-ssm-gateway-package

## Chaincode SSM
chaincode-ssm-package:
	make chaincode-ssm-package -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ssm

## Chaincode EX02
chaincode-ex02-package:
	make chaincode-ex02-package -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ex02


## hfabric Sandbox ca
hfabric-sandbox-ca-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C hfabric-sandbox/hfabric-sandbox-ca

## hfabric Sandbox cli
hfabric-sandbox-cli-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C hfabric-sandbox/hfabric-sandbox-cli

## hfabric Sandbox orderer
hfabric-sandbox-orderer-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C hfabric-sandbox/hfabric-sandbox-orderer

## hfabric Sandbox peer
hfabric-sandbox-peer-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C hfabric-sandbox/hfabric-sandbox-peer

## hfabric Sandbox ssm gateway
hfabric-sandbox-ssm-gateway-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C hfabric-sandbox/hfabric-sandbox-ssm-gateway
