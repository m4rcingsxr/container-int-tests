package testrepositories

internal object TestSchemaManager {

  fun validateSchema() {
    SessionInitializer.sessionFactory.schemaManager.validate()
  }

  fun truncateSchema() {
    SessionInitializer.sessionFactory.schemaManager.truncate()
  }
}
