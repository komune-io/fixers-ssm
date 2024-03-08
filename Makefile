VERSION = $(shell cat VERSION)
DOCKER_REPOSITORY = ghcr.io/

STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= ${DOCKER_REPOSITORY}komune/ssm-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest

CHAINCODE_APP_NAME	   	 	:= ghcr.io/komune-io/chaincode-api-gateway
CHAINCODE_APP_IMG	    	:= ${CHAINCODE_APP_NAME}:${VERSION}
CHAINCODE_APP_PACKAGE	   	:= :chaincode:chaincode-api:chaincode-api-gateway:bootBuildImage


.PHONY: version

lint: lint-libs
build: build-libs
test: test-libs
package: package-libs

# Old task
libs: package-kotlin
docs: package-storybook push-storybook
package-kotlin: build-libs test-libs package-libs

lint-libs:
	echo 'No Lint'
	#./gradlew detekt

build-libs:
	./gradlew build --scan -x test

test-libs:
	echo 'No Tests'
#	./gradlew test

package-libs: build-libs
	./gradlew publishToMavenLocal publish

package-storybook:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

push-storybook:
	@docker push ${STORYBOOK_IMG}

version:
	@VERSION=$$(cat VERSION); \
	echo "$$VERSION"

docker-chaincode-api-gateway-build:
	VERSION=${VERSION} ./gradlew build ${CHAINCODE_APP_PACKAGE} -x test --stacktrace

docker-chaincode-api-gateway-push:
	@docker push ${CHAINCODE_APP_IMG}


## DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
