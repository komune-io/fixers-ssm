package ssm.sdk.core.invoke.query

import ssm.sdk.core.invoke.builder.HasGet
import ssm.sdk.core.invoke.builder.HasList
import ssm.sdk.core.invoke.builder.QueryBuilder

class AgentQuery : QueryBuilder(SsmQueryName.USER), HasGet, HasList
