include ../../.env_version

SSM_CHAINCODE_DOCKERFILE 	:= Dockerfile
SSM_CHAINCODE_NAME 			:= ${DOCKER_REPOSITORY}komune-io/c2-chaincode-ssm
SSM_CHAINCODE_IMG 			:= ${SSM_CHAINCODE_NAME}:${VERSION}

.PHONY: lint build test publish promote

lint: chaincode-ssm-lint
build: chaincode-ssm-build

test:
	echo 'No Lint'

build: chaincode-ssm-build

publish: chaincode-ssm-push

promote:
	echo 'No Lint'

# chaincode-ssm
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

chaincode-ssm-inspect:
	@docker run -it ${SSM_CHAINCODE_IMG} sh
