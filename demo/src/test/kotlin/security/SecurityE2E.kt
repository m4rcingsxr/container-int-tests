package security

import asserter.BaseTest
import com.example.Role
import com.example.User
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.smallrye.jwt.auth.principal.DefaultJWTParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testrepositories.SessionInitializer
import java.time.Duration
import java.time.Instant

@QuarkusTest
class SecurityE2E : BaseTest() {
  private val jwtParser = DefaultJWTParser()

  @Test
  fun `should return 200 and generate jwt with valid claims`() {

    Given {
      auth().basic("user", "pass")
      contentType(ContentType.JSON)
    } When
      {
        post("/api/v1/security/login")
      } Then
      {
        statusCode(200)
      } Extract
      {
        val jwt = jwtParser.parseOnly(body().jsonPath().getString("accessToken"))

        assertJwtClaims(
          jwt.subject,
          expectedUsername = "user",
          apiGroups = jwt.groups,
          expectedGroups = setOf("TAX_USER"),
          apiIssuedAt = jwt.issuedAtTime,
          apiExpiresAt = jwt.expirationTime
        )
      }
  }

  @Test
  fun `should return 401 on invalid credentials`() {
    Given {
      auth().basic("user", "invalidPassword")
      contentType(ContentType.JSON)
    } When {
      post("/api/v1/security/login")
    } Then {
      statusCode(401)
    }
  }

  @Suppress("LongParameterList")
  private fun assertJwtClaims(
    apiUsername: String,
    expectedUsername: String,
    apiGroups: Set<String>,
    expectedGroups: Set<String>,
    apiIssuedAt: Long,
    apiExpiresAt: Long
  ) {
    val issuedAt = Instant.ofEpochSecond(apiIssuedAt)
    val expiresAt = Instant.ofEpochSecond(apiExpiresAt)
    val issuedAtOffset = Duration.between(issuedAt, Instant.now()).abs().seconds

    Assertions.assertEquals(expectedUsername, apiUsername)
    Assertions.assertEquals(expectedGroups, apiGroups)

    Assertions.assertEquals(Duration.ofHours(1).seconds, Duration.between(issuedAt, expiresAt).seconds)

    Assertions.assertTrue(issuedAtOffset <= MAX_ISSUED_AT_DRIFT_SECONDS)
  }

  companion object {
    private const val MAX_ISSUED_AT_DRIFT_SECONDS = 2L
  }

  @BeforeEach
  fun setup() {
    SessionInitializer.sessionFactory.inTransaction { session ->
      val user = User(
        username = "user",
        password = $$"$2a$12$VE0ywPNceH0DuPLL6unlOuPFDh5wDSSD4oNA7TfBH0hJONPgWB3Zy",
        role = setOf(Role.TAX_USER),
      )

      session.persist(user)
    }
  }
}
