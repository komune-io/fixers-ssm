package ssm.sdk.core.invoke.query

import ssm.sdk.core.invoke.builder.HasGet
import ssm.sdk.core.invoke.builder.HasList
import ssm.sdk.core.invoke.builder.QueryBuilder

class SsmQuery : QueryBuilder(SsmQueryName.SSM), HasGet, HasList
