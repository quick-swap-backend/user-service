package quickswap.userservice.application.auth

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import quickswap.userservice.adapter.persistence.auth.RefreshTokenEntity
import quickswap.userservice.application.auth.ports.out.RefreshTokenRepository
import quickswap.userservice.application.auth.ports.out.TokenProvider
import quickswap.userservice.application.auth.ports.out.TokenResolver
import quickswap.userservice.application.user.ports.`in`.UserFinder
import quickswap.userservice.domain.shared.PasswordEncoder
import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.Password
import quickswap.userservice.fixture.UserFixture
import java.time.LocalDateTime

class AuthModifyServiceTest {

  val userFinder: UserFinder = mockk()
  val passwordEncoder: PasswordEncoder = mockk()
  val tokenProvider: TokenProvider = mockk()
  val tokenResolver: TokenResolver = mockk()
  val refreshTokenRepository: RefreshTokenRepository = mockk()
  val modifyService: AuthModifyService =
    AuthModifyService(userFinder, passwordEncoder, tokenProvider, tokenResolver, refreshTokenRepository)

  @Test
  fun createToken() {
    val email = Email("test@test.com")
    val password = Password("test@123")

    every { passwordEncoder.encode(password.value) } returns "hash_${password.value}"
    every { passwordEncoder.matches(password.value, "hash_${password.value}") } returns true

    val userCreateRequest = UserFixture.getUserCreateRequest(email = email, password = password)
    val user = UserFixture.createUser(passwordEncoder = passwordEncoder, request = userCreateRequest)

    val refreshToken = "fake_refresh_token"
    val accessToken = "fake_access_token"

    every { userFinder.findByEmail(email) } returns user
    every { refreshTokenRepository.forceExpireAllByUserId(user.id.value) } returns Unit
    every { tokenProvider.generateAccessToken(user.id, user.email) } returns accessToken
    every { tokenProvider.generateRefreshToken(user.id) } returns refreshToken
    every { tokenResolver.getExpirationDateTime(any()) } returns LocalDateTime.MAX
    every { refreshTokenRepository.save(any()) } returns mockk()

    val (createdAccessToken, createdRefreshToken) = modifyService.createToken(email, password)

    assert(accessToken == createdAccessToken)
    assert(refreshToken == createdRefreshToken)

    /* --- */

    every { passwordEncoder.matches(any(), any()) } returns false
    assertThrows<IllegalArgumentException> { modifyService.createToken(email, password) }
  }

  @Test
  fun accessTokenRefresh() {
    val refreshTokenEntity: RefreshTokenEntity = mockk()
    val refreshToken = "refresh_token"
    val user = UserFixture.createUser()
    val accessToken = "access_token"

    every { refreshTokenRepository.findByToken(refreshToken) } returns refreshTokenEntity
    every { refreshTokenEntity.isExpired() } returns false
    every { tokenResolver.getUserIdFromToken(refreshToken) } returns user.id
    every { userFinder.findById(user.id) } returns user
    every { tokenProvider.generateAccessToken(user.id, user.email) } returns accessToken

    assert(modifyService.accessTokenRefresh(refreshToken) == accessToken)

    /* --- */
    every { refreshTokenEntity.isExpired() } returns true
    assertThrows<IllegalArgumentException> { modifyService.accessTokenRefresh(refreshToken) }

    every { refreshTokenRepository.findByToken(refreshToken) } returns null
    assertThrows<IllegalArgumentException> { modifyService.accessTokenRefresh(refreshToken) }
  }
}