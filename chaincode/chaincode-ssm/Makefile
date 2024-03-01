VERSION = $(shell cat ../../VERSION)
include ../.env_version

SSM_CHAINCODE_DOCKERFILE 	:= Dockerfile
SSM_CHAINCODE_NAME 			:= ${DOCKER_REPOSITORY}komune-io/chaincode-ssm
SSM_CHAINCODE_IMG 			:= ${SSM_CHAINCODE_NAME}:${VERSION}
SSM_CHAINCODE_LATEST		:= ${SSM_CHAINCODE_NAME}:latest

lint: chaincode-ssm-lint
build: chaincode-ssm-build


test:
	echo 'No Lint'

package: chaincode-ssm-package


# chaincode-ssm
chaincode-ssm-package: chaincode-ssm-build chaincode-ssm-push chaincode-ssm-push-latest

chaincode-ssm-lint:
	docker run --rm -i hadolint/hadolint < Dockerfile

chaincode-ssm-build:
	docker build --no-cache \
		--platform=linux/amd64 \
		--no-cache \
		--build-arg VERSION_FABRIC=${VERSION_FABRIC} \
		--build-arg VERSION_ALPINE=${VERSION_ALPINE} \
		--build-arg VERSION=${VERSION} \
		-f ${SSM_CHAINCODE_DOCKERFILE} \
		-t ${SSM_CHAINCODE_IMG} .

chaincode-ssm-push:
	@docker push ${SSM_CHAINCODE_IMG}

chaincode-ssm-push-latest:
	@docker tag ${SSM_CHAINCODE_IMG} ${SSM_CHAINCODE_LATEST}
	@docker push ${SSM_CHAINCODE_LATEST}

chaincode-ssm-inspect:
	@docker run -it ${SSM_CHAINCODE_IMG} sh
