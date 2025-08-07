package testrepositories

import org.eclipse.microprofile.config.ConfigProvider
import org.hibernate.SessionFactory
import org.hibernate.cfg.AvailableSettings
import org.hibernate.cfg.Configuration

internal object SessionInitializer {

  private const val PORT = "quarkus.datasource.devservices.port"

  val sessionFactory = initializeSessionFactory()

  private fun initializeSessionFactory(): SessionFactory {
    return Configuration().apply {
      setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:${ConfigProvider.getConfig().getValue(PORT, String::class.java)}/quarkus")
      setProperty(AvailableSettings.JAKARTA_JDBC_USER, "quarkus")
      setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "password")
      setProperty(AvailableSettings.SHOW_SQL, true)
      setProperty(AvailableSettings.FORMAT_SQL, true)
    }
      .buildSessionFactory()
  }
}
