include ../../.env_version
include ../config/.env
export

FABRIC_ORDERER_NAME		:= ${DOCKER_REPOSITORY}komune-io/c2-sandbox-orderer
FABRIC_ORDERER_IMG		:= ${FABRIC_ORDERER_NAME}:${VERSION}
FABRIC_ORDERER_LATEST 	:= ${FABRIC_ORDERER_NAME}:latest

package: lint build test push

test:
	@echo No test to run

lint:
	docker run --rm -i hadolint/hadolint < Dockerfile

build:
	docker build \
		 --platform=linux/amd64 \
		 --no-cache \
    	--build-arg VERSION_FABRIC=$$VERSION_FABRIC \
    	--build-arg COOP_CA_DOMAIN=$$orderer_org \
    	--build-arg COOP_ORDERER_HOSTNAME=$$orderer_hostname \
    	-f Dockerfile \
    	-t ${FABRIC_ORDERER_IMG} ..

push:
	@docker push ${FABRIC_ORDERER_IMG}

push-latest:
	@docker tag ${FABRIC_ORDERER_IMG} ${FABRIC_ORDERER_LATEST}
	@docker push ${FABRIC_ORDERER_LATEST}