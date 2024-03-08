package ssm.data.spring.autoconfigure.notused

//import ssm.data.dsl.config.DataSsmConfig

///**
// * @d2 model
// * @child [DataSsmConfig]
// * @child [ssm.couchdb.autoconfiguration.SsmCouchdbProperties]
// * @title SSM Configuration List
// * @page
// * The SDK provides an auto-configuration module for spring applications. \
// * The configuration is composed of two lists of [SSM Configurations][SsmConfigProperties] and [CouchDB Configurations][ssm.couchdb.autoconfiguration.SsmCouchdbProperties]
// * @@title SSM-TX/Configuration
// * @@example {
// * 	ssm: {
// *		couchdb: {
// *			smartbase: {
// *				url: "http://peer.sandbox.Komune.io:9000",
// *				username: "admin",
// *				password: "admin",
// *				serviceName: "ssm-couchdb"
// *			}
// *		},
// *		chaincode: {
// *			"smartcode-ssm": {
// *				baseUrl: "http://peer.sandbox.Komune.io:9000",
// *	 			channelId: "channel-komune",
// *	 			chaincodeId: "ssm-komune"
// *	 		}
// *		},
// * 		list: {
// *		 	ProductLogistic: {
// *			 	"1.0": {
// *				 	baseUrl: "http://peer.sandbox.Komune.io:9000",
// *				 	channel: "channel-komune",
// *				 	chaincode: "smartcode-ssm"
// *				}
// *			}
// *		}
// *	}
// * }
// */
//@ConfigurationProperties(prefix = "ssm")
//@ConstructorBinding
//class SsmConfigProperties(
//	val list: DataSsmConfig,
//	val chaincode: Map<String, SsmChaincodeProperties>,
//)
