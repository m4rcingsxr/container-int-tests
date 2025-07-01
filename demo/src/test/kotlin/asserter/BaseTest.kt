package asserter

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import testrepositories.TestSchemaManager

abstract class BaseTest {

  @BeforeEach
  fun beforeEach() {
    TestSchemaManager.truncateSchema()
  }

  companion object {
    @JvmStatic
    @BeforeAll
    fun beforeAll() {
      TestSchemaManager.validateSchema()
    }
  }
}
