VERSION = $(shell cat ../VERSION)
include ../.env_version
export

package: c2-sandbox-ca-package c2-sandbox-cli-package c2-sandbox-orderer-package c2-sandbox-peer-package c2-sandbox-ssm-gateway-package

## c2 Sandbox ca
c2-sandbox-ca-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-sandbox-ca

## c2 Sandbox cli
c2-sandbox-cli-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-sandbox-cli

## c2 Sandbox orderer
c2-sandbox-orderer-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-sandbox-orderer

## c2 Sandbox peer
c2-sandbox-peer-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-sandbox-peer

## c2 Sandbox ssm gateway
c2-sandbox-ssm-gateway-package:
	make package -e VERSION=${VERSION} -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-sandbox-ssm-gateway
