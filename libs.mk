VERSION = $(shell cat VERSION)

.PHONY: lint build test test-pre publish promote

lint:
	./gradlew detekt

build:
	@make -f docker.mk build
	VERSION=$(VERSION) ./gradlew clean build publishToMavenLocal -x test

test-pre:
	@make dev up
	@echo "///////////////////////1"
	@make dev c2-sandbox logs
	@echo "///////////////////////2"
	@make dev c2-sandbox-ssm logs
	@echo "///////////////////////3"
	@make dev c2-sandbox-ex02 logs
	@echo "///////////////////////4"
	@make dev up
	@echo "///////////////////////5"
	@make dev c2-sandbox logs
	@echo "///////////////////////6"
	@make dev c2-sandbox-ssm logs
	@echo "///////////////////////7"
	@make dev c2-sandbox-ex02 logs
	@echo "///////////////////////8"
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
