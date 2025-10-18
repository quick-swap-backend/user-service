package quickswap.userservice.adapter.webapi

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import quickswap.userservice.adapter.webapi.dto.UserCreateResponse
import quickswap.userservice.application.user.ports.`in`.UserCreator
import quickswap.userservice.domain.user.UserCreateRequest

@RestController("/api/v1")
class UserController(
  val userCreator: UserCreator
) {

  @PostMapping("/user")
  fun createUser(@RequestBody request: UserCreateRequest) {
    val result = userCreator.create(request)
    ResponseEntity.ok(UserCreateResponse(result.id.value, result.email.value))
  }

}