Feature:

  Scenario Outline: Inject couchdb function
    Given The application parameters
      | ssm.couchdb.url         | http://localhost:5984 |
      | ssm.couchdb.username    | couchdb               |
      | ssm.couchdb.password    | couchdb               |
      | ssm.couchdb.serviceName | ssm-couchdb-test      |
    When I build a valid spring application context
    Then Instance of "<functionName>" is an injectable bean

    Examples:
      | functionName                            |
      | couchdbSsmSessionStateListQueryFunction |
      | couchdbDatabaseListQueryFunction        |
      | couchdbDatabaseGetQueryFunction         |
      | couchdbSsmGetQueryFunction              |
      | couchdbSsmListQueryFunction             |
      | couchdbSsmSessionStateListQueryFunction |
      | couchdbChaincodeListQueryFunction       |
      | couchdbAdminListQueryFunction           |
      | couchdbUserListQueryFunction            |
      | couchdbSsmSessionStateGetQueryFunction  |

  Scenario Outline: No inject couchdb function when config is not provided.
    When I get an empty spring application context
    Then Instance of "<functionName>" is not injectable bean

    Examples:
      | functionName                            |
      | couchDbSsmSessionStateListQueryFunction |
      | couchdbDatabaseListQueryFunction        |
      | couchDbDatabaseGetQueryFunction         |
      | couchdbSsmGetQueryFunction              |
      | couchDbSsmListQueryFunction             |
      | couchDbSsmSessionStateListQueryFunction |
      | couchdbChaincodeListQueryFunction       |
      | couchdbAdminListQueryFunction           |
      | couchDbUserListQueryFunction            |
      | couchdbSsmSessionStateGetQueryFunction  |
