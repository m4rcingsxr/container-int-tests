package security

import io.quarkus.test.junit.QuarkusIntegrationTest
import org.junit.jupiter.api.Test
import testrepositories.SessionInitializer

@QuarkusIntegrationTest
class SecurityE2EIT {
    @Test
    fun `sessionFactory initialized sucessfully`() {
      val sessionFactory = SessionInitializer.sessionFactory
      println("FACTORY OPEN " + sessionFactory.isOpen)
    }
}
