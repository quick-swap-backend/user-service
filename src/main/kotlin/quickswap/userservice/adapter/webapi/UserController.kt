package quickswap.userservice.adapter.webapi

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import quickswap.userservice.adapter.webapi.dto.UserCreateResponse
import quickswap.userservice.application.user.ports.`in`.UserCreator
import quickswap.userservice.domain.user.UserCreateRequest

@RequestMapping("/api/v1")
@RestController
class UserController(
  private val userCreator: UserCreator
) {

  @PostMapping("/user")
  fun createUser(@RequestBody request: UserCreateRequest): ResponseEntity<UserCreateResponse> {
    val result = userCreator.create(request)
    return ResponseEntity.ok(UserCreateResponse.of(result))
  }
}