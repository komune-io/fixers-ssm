include ../.env_version

export

package: chaincode-ssm-package chaincode-ex02-package

## Chaincode SSM
chaincode-ssm-package:
	make chaincode-ssm-package -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ssm

## Chaincode EX02
chaincode-ex02-package:
	make chaincode-ex02-package -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ex02
