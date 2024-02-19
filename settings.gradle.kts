
rootProject.name = "fixers-ssm"

include(
	"ssm-bdd:ssm-bdd-config",
	"ssm-bdd:ssm-bdd-features",
	"ssm-bdd:ssm-bdd-spring-autoconfigure"
)

include(
	"ssm-chaincode:ssm-chaincode-bdd",
	"ssm-chaincode:ssm-chaincode-dsl",
	"ssm-chaincode:ssm-chaincode-f2",
	"ssm-chaincode:ssm-chaincode-f2-client",
)

include(
	"ssm-couchdb:ssm-couchdb-bdd",
	"ssm-couchdb:ssm-couchdb-sdk",
	"ssm-couchdb:ssm-couchdb-dsl",
	"ssm-couchdb:ssm-couchdb-f2",
)

include(
	"ssm-data:ssm-data-bdd",
	"ssm-data:ssm-data-client",
	"ssm-data:ssm-data-dsl",
	"ssm-data:ssm-data-f2",
	"ssm-data:ssm-data-sync",
)

include(
	"ssm-sdk:ssm-sdk-bdd",
	"ssm-sdk:ssm-sdk-dsl",
	"ssm-sdk:ssm-sdk-core",
	"ssm-sdk:ssm-sdk-json",
	"ssm-sdk:ssm-sdk-sign",
	"ssm-sdk:ssm-sdk-sign-rsa-key",
)

include(
	"ssm-spring:ssm-chaincode-spring-boot-starter",
	"ssm-spring:ssm-couchdb-spring-boot-starter",
	"ssm-spring:ssm-data-spring-boot-starter",
	"ssm-spring:ssm-tx-spring-boot-starter",
	"ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-config-spring-boot-starter",
	"ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-create-ssm-spring-boot-starter",
	"ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-init-ssm-spring-boot-starter",
	"ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-session-perform-action-spring-boot-starter",
	"ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-session-start-spring-boot-starter"
)

include(
	"sample:ssm-full",
	"sample:ssm-full-ext",
)

include(
	"ssm-tx:ssm-tx-bdd",
	"ssm-tx:ssm-tx-dsl",
	"ssm-tx:ssm-tx-f2",
)
