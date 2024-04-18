VERSION = $(shell cat VERSION)
DOCKER_REPOSITORY = ghcr.io/

CHAINCODE_APP_NAME	   	 	:= ghcr.io/komune-io/c2-chaincode-api-gateway
CHAINCODE_APP_IMG	    	:= ${CHAINCODE_APP_NAME}:${VERSION}
CHAINCODE_APP_PACKAGE	   	:= :c2-chaincode:chaincode-api:chaincode-api-gateway:bootBuildImage

.PHONY: lint build test publish promote

lint:
	make lint -e DOCKER_REPOSITORY=ghcr.io/ -C c2-chaincode -e VERSION=$(VERSION)
	make lint -e DOCKER_REPOSITORY=ghcr.io/ -C c2-sandbox -e VERSION=$(VERSION)

build: docker-chaincode-api-gateway-build
	make build -e DOCKER_REPOSITORY=ghcr.io/ -C c2-chaincode -e VERSION=$(VERSION)
	make build -e DOCKER_REPOSITORY=ghcr.io/ -C c2-sandbox -e VERSION=$(VERSION)

publish: docker-chaincode-api-gateway-publish
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C c2-chaincode -e VERSION=$(VERSION)
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C c2-sandbox -e VERSION=$(VERSION)

promote:
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C c2-chaincode -e VERSION=$(VERSION)
	make publish -e DOCKER_REPOSITORY=ghcr.io/ -C c2-sandbox -e VERSION=$(VERSION)

## chaincode-api
docker-chaincode-api-gateway-build:
	VERSION=$(VERSION) ./gradlew build ${CHAINCODE_APP_PACKAGE} --imageName ${CHAINCODE_APP_IMG} -x test

docker-chaincode-api-gateway-publish:
	VERSION=$(VERSION) docker push ${CHAINCODE_APP_IMG}
