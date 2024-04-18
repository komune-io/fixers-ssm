
include ../.env_version
export

.PHONY: lint build test publish promote

lint:
	make lint -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ex02
	make lint -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ssm

build:
	make build -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ex02
	make build -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ssm

publish:
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ex02
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ssm

promote:
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ex02
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C chaincode-ssm

