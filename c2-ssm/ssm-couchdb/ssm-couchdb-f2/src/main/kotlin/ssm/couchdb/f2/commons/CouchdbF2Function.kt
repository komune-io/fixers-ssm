package ssm.couchdb.f2.commons

import ssm.chaincode.dsl.model.ChaincodeId
import ssm.chaincode.dsl.model.ChannelId
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.ChaincodeUriDTO
import ssm.chaincode.dsl.model.uri.burst

fun chainCodeDbName(channelId: ChannelId, chaincodeId: ChaincodeId) = "${channelId}_$chaincodeId"

fun ChaincodeUriDTO.chainCodeDbName(): String {
	val uri = burst()
	return chainCodeDbName(uri.channelId, uri.chaincodeId)
}

fun ChaincodeUri.chainCodeDbName(): String {
	return chainCodeDbName(channelId, chaincodeId)
}
