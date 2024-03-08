include ../../.env_version
include ../config/.env
export

FABRIC_CLI_NAME			:= ${DOCKER_REPOSITORY}komune-io/c2-sandbox-cli
FABRIC_CLI_IMG			:= ${FABRIC_CLI_NAME}:${VERSION}
FABRIC_CLI_LATEST 		:= ${FABRIC_CLI_NAME}:latest

package: clean lint build test push

clean:
	rm -fr build

lint:
	docker run --rm -i hadolint/hadolint < Dockerfile

test:
	@echo No test to run

build:
	-@mkdir build
	echo $$VERSION_FABRIC
	@sed s/__SSM_VERSION__/${SSM_VERSION}/ Dockerfile > build/Dockerfile
	@docker build \
 		--platform=linux/amd64 \
		--no-cache \
    	--build-arg SSM_VERSION=$$SSM_VERSION \
    	--build-arg VERSION_FABRIC=$$VERSION_FABRIC \
    	--build-arg VERSION_CHAINCODE_API=$$VERSION \
    	--build-arg COOP_CA_DOMAIN=$$orderer_org \
    	--build-arg COOP_PEER=peer0 \
    	--build-arg COOP_PEER_DOMAIN=$$cli_ORGA \
    	--build-arg COOP_PEER_MSP=$$peer_msp \
    	--build-arg CLI_USER=$$cli_user \
    	-f build/Dockerfile \
    	-t ${FABRIC_CLI_IMG} ..

push:
	@docker push ${FABRIC_CLI_IMG}

push-latest:
	@docker tag ${FABRIC_CLI_IMG} ${FABRIC_CLI_LATEST}
	@docker push ${FABRIC_CLI_LATEST}
