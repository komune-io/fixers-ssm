VERSION = $(shell cat VERSION)

.PHONY: lint build test test-pre publish promote

lint:
	./gradlew detekt

build:
	VERSION=$(VERSION) ./gradlew clean build publishToMavenLocal -x test

test-pre:
	@make dev up
	@make dev c2-sandbox-ssm logs
	@make dev up
	sudo echo "127.0.0.1 ca.bc-coop.bclan" | sudo tee -a /etc/hosts
	sudo echo "127.0.0.1 peer0.bc-coop.bclan" | sudo tee -a /etc/hosts
	sudo echo "127.0.0.1 orderer.bclan" | sudo tee -a /etc/hosts

test:
	./gradlew test
test-post:
	@make dev down

publish:
	VERSION=$(VERSION) PKG_MAVEN_REPO=github ./gradlew publish --info
promote:
	VERSION=$(VERSION) PKG_MAVEN_REPO=sonatype_oss ./gradlew publish

.PHONY: version
version:
	@echo "$(VERSION)"
