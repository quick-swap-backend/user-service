package quickswap.userservice.application.auth

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import quickswap.commons.application.shared.ports.out.TokenResolver
import quickswap.commons.domain.shared.PasswordEncoder
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Password
import quickswap.userservice.adapter.persistence.auth.RefreshTokenEntity
import quickswap.userservice.application.auth.ports.`in`.TokenCreator
import quickswap.userservice.application.auth.ports.out.RefreshTokenRepository
import quickswap.userservice.application.auth.ports.out.TokenProvider
import quickswap.userservice.application.user.ports.`in`.UserFinder
import quickswap.userservice.domain.user.User

@Transactional
@Service
class AuthModifyService(
  private val userFinder: UserFinder,
  private val passwordEncoder: PasswordEncoder,
  private val tokenProvider: TokenProvider,
  private val tokenResolver: TokenResolver,
  private val refreshTokenRepository: RefreshTokenRepository
): TokenCreator {

  override fun createToken(email: Email, password: Password): Pair<String, String> {
    val foundUser = findAndVarifyUser(email, password)

    return generateAccessTokenAndRefreshToken(foundUser)
  }

  private fun findAndVarifyUser(email: Email, password: Password): User {
    val foundUser = userFinder.findByEmail(email)

    if(!passwordEncoder.matches(password.value, foundUser.hashedPassword)) {
      throw IllegalArgumentException("로그인에 실패했습니다.")
    }

    return foundUser
  }

  private fun generateAccessTokenAndRefreshToken(user:User): Pair<String, String> {
    refreshTokenRepository.forceExpireAllByUserId(user.id.value)
    val accessToken = tokenProvider.generateAccessToken(user.id, user.email)
    val refreshToken = tokenProvider.generateRefreshToken(user.id)

    val entity = RefreshTokenEntity.of(user.id, refreshToken, tokenResolver.getExpirationDateTime(refreshToken))
    refreshTokenRepository.save(entity)

    return (accessToken to refreshToken)
  }

  override fun accessTokenRefresh(refreshToken: String): String {
    val foundRefreshTokenToken = findAndVerifyRefreshTokenEntity(refreshToken)

    val userId = tokenResolver.getUserIdFromToken(refreshToken)
    val foundUser = userFinder.findById(userId)

    return tokenProvider.generateAccessToken(foundUser.id, foundUser.email)
  }

  private fun findAndVerifyRefreshTokenEntity(refreshToken: String): RefreshTokenEntity {
    val foundRefreshToken = refreshTokenRepository.findByToken(refreshToken)
      ?: throw IllegalArgumentException("잘못된 요청입니다. refreshToken: $refreshToken")

    if (foundRefreshToken.isExpired()) {
      throw IllegalArgumentException("이미 만료된 토큰입니다. refreshToken: $refreshToken")
    }

    return foundRefreshToken
  }

}