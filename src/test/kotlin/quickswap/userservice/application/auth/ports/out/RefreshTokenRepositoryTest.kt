package quickswap.userservice.application.auth.ports.out

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import quickswap.commons.domain.shared.id.UserId
import quickswap.userservice.adapter.persistence.auth.RefreshTokenEntity
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DataJpaTest
class RefreshTokenRepositoryTest {

  @Autowired
  lateinit var repository: RefreshTokenRepository

  @Test
  fun existsActiveTokenByUserId() {
    val userId = UserId(UUID.randomUUID().toString())
    val token = "this_is_a_fake_token"
    val futureTime = LocalDateTime.now().plusYears(1)
    val tokenEntity = RefreshTokenEntity.of(userId, token, futureTime)

    repository.save(tokenEntity)

    assertTrue { repository.existsActiveTokenByUserId(userId.value) }

    /* === */

    val expiryDateUserId = UserId(UUID.randomUUID().toString())
    val expiryDateTokenEntity = RefreshTokenEntity.of(expiryDateUserId, token, LocalDateTime.now())

    repository.save(expiryDateTokenEntity)

    assertFalse { repository.existsActiveTokenByUserId(expiryDateUserId.value) }

    /* === */

    val forceExpiredUserId = UserId(UUID.randomUUID().toString())
    val forceExpiredTokenEntity = RefreshTokenEntity.of(forceExpiredUserId, token, LocalDateTime.now())

    forceExpiredTokenEntity.forceExpiry()

    repository.save(forceExpiredTokenEntity)

    assertFalse { repository.existsActiveTokenByUserId(forceExpiredUserId.value) }

  }

  @Test
  fun forceExpireAllByUserId() {
    val userId = UserId(UUID.randomUUID().toString())
    val firstToken = "this_is_a_first_fake_token"
    val futureTime = LocalDateTime.now().plusYears(1)
    val firstTokenEntity = RefreshTokenEntity.of(userId, firstToken, futureTime)

    repository.save(firstTokenEntity)
    var foundFalseToken = repository.findByToken(firstToken)
    assertFalse { foundFalseToken!!.isForceExpired }

    repository.forceExpireAllByUserId(userId.value)

    foundFalseToken = repository.findByToken(firstToken)
    assertTrue { foundFalseToken!!.isForceExpired }
  }

  @Test
  fun findByToken() {
    val userId = UserId(UUID.randomUUID().toString())
    val token = "this_is_a_fake_token"
    val futureTime = LocalDateTime.now().plusYears(1)
    val tokenEntity = RefreshTokenEntity.of(userId, token, futureTime)

    repository.save(tokenEntity)
    val foundEntity = repository.findByToken(token)

    assertNotNull { foundEntity }
    assert(foundEntity!!.userId == userId.value)
    assert(foundEntity.token == token)
  }

}