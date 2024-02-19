STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/ssm-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest

libs: package-kotlin
docs: package-storybook

package-kotlin: build-libs publish-libs

build-libs:
	./gradlew build

test-libs:
	./gradlew test

publish-libs: build-libs
	VERSION=${VERSION} ./gradlew publishToMavenLocal publish


package-storybook:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
    @docker push ${STORYBOOK_IMG}


## DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
