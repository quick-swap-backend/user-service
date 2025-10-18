package quickswap.userservice.fixture

import quickswap.userservice.domain.shared.PasswordEncoder

class SecurityFixture {

  companion object {
    fun getPasswordEncoder() : PasswordEncoder {
      return object : PasswordEncoder {

        override fun encode(password: String): String {
          return "hashed_$password"
        }

        override fun matches(password: String, hashedPassword: String): Boolean {
          return encode(password) == hashedPassword
        }
      }
    }
  }
}