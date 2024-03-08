VERSION = $(shell cat ../../VERSION)
include ../.env_version

EX02_CHAINCODE_DOCKERFILE 	:= Dockerfile
EX02_CHAINCODE_NAME 		:= ${DOCKER_REPOSITORY}komune-io/chaincode-ex02
EX02_CHAINCODE_IMG 			:= ${EX02_CHAINCODE_NAME}:${VERSION}
EX02_CHAINCODE_LATEST		:= ${EX02_CHAINCODE_NAME}:latest

lint: chaincode-ex02-lint
build: chaincode-ex02-build

test:
	echo 'No Lint'

package: chaincode-ex02-package

# chaincode-ex02
chaincode-ex02-package: chaincode-ex02-build chaincode-ex02-push chaincode-ex02-push-latest

chaincode-ex02-lint:
	docker run --rm -i hadolint/hadolint < Dockerfile

chaincode-ex02-build:
	echo ${VERSION}
	docker build \
 		--platform=linux/amd64 \
		--no-cache \
		--build-arg VERSION=${VERSION} \
		--build-arg VERSION_FABRIC=${VERSION_FABRIC} \
		--build-arg VERSION_ALPINE=${VERSION_ALPINE} \
		-f ${EX02_CHAINCODE_DOCKERFILE} \
		-t ${EX02_CHAINCODE_IMG} .

chaincode-ex02-push:
	@docker push ${EX02_CHAINCODE_IMG}

chaincode-ex02-push-latest:
	@docker tag ${EX02_CHAINCODE_IMG} ${EX02_CHAINCODE_LATEST}
	@docker push ${EX02_CHAINCODE_LATEST}

chaincode-ex02-inspect:
	@docker run -it ${EX02_CHAINCODE_IMG} sh
