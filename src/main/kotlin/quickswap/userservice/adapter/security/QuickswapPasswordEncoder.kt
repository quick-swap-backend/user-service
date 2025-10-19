package quickswap.userservice.adapter.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import quickswap.userservice.domain.shared.PasswordEncoder

@Component
class QuickswapPasswordEncoder(
  val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
): PasswordEncoder {

  override fun encode(password: String): String {
    return bCryptPasswordEncoder.encode(password)
  }

  override fun matches(password: String, hashedPassword: String): Boolean {
    return bCryptPasswordEncoder.matches(password, hashedPassword)
  }

}