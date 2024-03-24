VERSION = $(shell cat VERSION)
DOCKER_REPOSITORY = ghcr.io/

CHAINCODE_APP_NAME	   	 	:= ghcr.io/komune-io/chaincode-api-gateway
CHAINCODE_APP_IMG	    	:= ${CHAINCODE_APP_NAME}:${VERSION}
CHAINCODE_APP_PACKAGE	   	:= :c2-chaincode:chaincode-api:chaincode-api-gateway:bootBuildImage

.PHONY: version

lint: lint-libs
build: build-libs
test-pre:
	@make dev up
	@make dev c2-sandbox-ssm logs
	@make dev up
test: test-libs
test-post:
	@make dev down
package: package-libs

# Old task
libs: package-kotlin
package-kotlin: lint-libs build-libs test-libs package-libs

lint-libs:
	./gradlew detekt

build-libs:
	./gradlew build publishToMavenLocal -x test

test-libs:
	./gradlew test

package-libs: build-libs
	./gradlew publish

version:
	echo "$$VERSION"

chaincode-api-gateway-package: docker-chaincode-api-gateway-build docker-chaincode-api-gateway-push


docker-chaincode-api-gateway-build:
	VERSION=${VERSION} ./gradlew build publishToMavenLocal ${CHAINCODE_APP_PACKAGE} -x test --stacktrace

docker-chaincode-api-gateway-push:
	@docker push ${CHAINCODE_APP_IMG}


## Chaincode
chaincode: chaincode-api-gateway-package
	make package -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-chaincode

## Sandbox
sandbox:
	make package -e DOCKER_REPOSITORY=${DOCKER_REPOSITORY} -C c2-sandbox

## ssm
ssm: package-kotlin

## DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
