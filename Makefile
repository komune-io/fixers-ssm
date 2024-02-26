STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= komune/ssm-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest

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
	./gradlew detekt

build-libs:
	./gradlew build --scan

test-libs:
	./gradlew test

package-libs: build-libs
	./gradlew publishToMavenLocal publish

package-storybook:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

push-storybook:
	@docker push ${STORYBOOK_IMG}

version:
	@VERSION=$$(cat VERSION); \
	echo "$$VERSION"

## DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
