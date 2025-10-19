package quickswap.userservice.adapter.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import quickswap.userservice.application.user.ports.`in`.UserFinder
import quickswap.userservice.domain.user.Email

@Service
class CustomUserDetailsService(
  private val userService: UserFinder
) : UserDetailsService {

  override fun loadUserByUsername(email: String): UserDetails {
    val user = userService.findByEmail(Email(email))

    return User.builder()
      .username(email)
      .password(user.hashedPassword)
      .authorities(emptyList())
      .build()
  }
}