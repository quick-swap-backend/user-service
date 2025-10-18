package quickswap.userservice.domain.shared

interface PasswordEncoder {
  fun encode(password: String) :String
  fun matches(password: String, hashedPassword: String) : Boolean
}