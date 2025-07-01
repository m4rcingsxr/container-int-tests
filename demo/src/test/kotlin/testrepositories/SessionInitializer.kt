package testrepositories

import com.example.User
import org.hibernate.SessionFactory
import org.hibernate.cfg.AvailableSettings
import org.hibernate.cfg.Configuration
import utils.TestUtils

internal object SessionInitializer {

  private const val PORT = "quarkus.datasource.devservices.port"
  private const val USERNAME = "quarkus.datasource.devservices.username"
  private const val PASSWORD = "quarkus.datasource.devservices.password"
  private const val PHYSICAL_NAMING_STRATEGY = "quarkus.hibernate-orm.physical-naming-strategy"

  private val annotatedClasses = listOf(
    User::class.java,
  ).toTypedArray()

  val sessionFactory = initializeSessionFactory()

  private fun initializeSessionFactory(): SessionFactory {
    val port = TestUtils.getConfigurationValue(property = PORT)
    val username = TestUtils.getConfigurationValue(property = USERNAME)
    val password = TestUtils.getConfigurationValue(property = PASSWORD)
    val namingStrategy = TestUtils.getConfigurationValue(property = PHYSICAL_NAMING_STRATEGY)

    return Configuration().apply {
      setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:$port/quarkus")
      setProperty(AvailableSettings.JAKARTA_JDBC_USER, username)
      setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, username)
      setProperty(AvailableSettings.SHOW_SQL, true)
      setProperty(AvailableSettings.FORMAT_SQL, true)
      setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY, namingStrategy)
      addAnnotatedClasses(*annotatedClasses)
    }
      .buildSessionFactory()
  }
}
