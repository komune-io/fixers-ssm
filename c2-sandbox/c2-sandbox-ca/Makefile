include ../../.env_version
include ../config/.env
export

FABRIC_CA_NAME			:= ${DOCKER_REPOSITORY}komune-io/c2-sandbox-ca
FABRIC_CA_IMG			:= ${FABRIC_CA_NAME}:${VERSION}
FABRIC_CA_LATEST 		:= ${FABRIC_CA_NAME}:latest

package: build test push

clean:
	@echo "No clean to run"

test:
	@echo "No test to run"

lint:
	docker run --rm -i hadolint/hadolint < Dockerfile

build:
	@docker build \
		 --platform=linux/amd64 \
		 --no-cache \
         --build-arg VERSION=$$VERSION \
         --build-arg VERSION_FABRIC_CA=$$VERSION_FABRIC_CA \
         --build-arg COOP_CA_HOSTNAME=$$CA_HOSTNAME \
         --build-arg COOP_PEER_DOMAIN=$$cli_ORGA \
         --build-arg ca__CA_KEYFILE=$$ca__CA_KEYFILE \
         --build-arg ca__TLS_KEYFILE=$$ca__TLS_KEYFILE \
         --build-arg ca__ADMIN=$$ca__ADMIN \
         --build-arg ca__PASSWD=$$ca__PASSWD \
         -f Dockerfile \
         -t ${FABRIC_CA_IMG} ..

push:
	@docker push ${FABRIC_CA_IMG}

push-latest:
	@docker tag ${FABRIC_CA_IMG} ${FABRIC_CA_LATEST}
	@docker push ${FABRIC_CA_LATEST}