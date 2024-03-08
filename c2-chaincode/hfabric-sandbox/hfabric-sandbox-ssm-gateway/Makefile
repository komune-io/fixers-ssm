include ../../.env_version
include ../config/.env
export

VERSION_CHAINCODE_API = $(shell cat ../../../version)


FABRIC_SSM_REST_NAME	:= ${DOCKER_REPOSITORY}komune-io/hfabric-sandbox-ssm-gateway
FABRIC_SSM_REST_IMG		:= ${FABRIC_SSM_REST_NAME}:${VERSION}
FABRIC_SSM_REST_LATEST 	:= ${FABRIC_SSM_REST_NAME}:latest

clean:
	@echo No test to run

lint:
	docker run --rm -i hadolint/hadolint < Dockerfile

test:
	@echo No test to run

package: lint build test push

build:
	@docker build \
	 	--platform=linux/amd64 \
	 	--no-cache \
    	--build-arg DOCKER_REPOSITORY=$$DOCKER_REPOSITORY \
    	--build-arg VERSION_CHAINCODE_API=$$VERSION_CHAINCODE_API \
    	--build-arg coop_channel=$$coop_channel \
    	--build-arg coop_ccid=$$coop_ccid \
		--build-arg coop_user_org=$$coop_user_org \
		--build-arg coop_endorsers=$$coop_endorsers \
    	--build-arg ca__ADMIN=$$ca__ADMIN \
    	--build-arg ca__PASSWD=$$ca__PASSWD \
    	-f Dockerfile \
    	-t ${FABRIC_SSM_REST_IMG} ..

push:
	@docker push ${FABRIC_SSM_REST_IMG}

push-latest:
	@docker tag ${FABRIC_SSM_REST_IMG} ${FABRIC_SSM_REST_LATEST}
	@docker push ${FABRIC_SSM_REST_LATEST}
