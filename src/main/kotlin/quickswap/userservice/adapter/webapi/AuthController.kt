package quickswap.userservice.adapter.webapi

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import quickswap.userservice.adapter.webapi.dto.LoginResponse
import quickswap.userservice.adapter.webapi.dto.RefreshRequest
import quickswap.userservice.adapter.webapi.dto.RefreshResponse
import quickswap.userservice.application.auth.dto.LoginRequest
import quickswap.userservice.application.auth.ports.`in`.TokenCreator

@RequestMapping("/api/v1")
@RestController
class AuthController(
  val tokenCreator: TokenCreator
) {

  @PostMapping("/login")
  fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
    val (accessToken, refreshToken) = tokenCreator.createToken(request.email, request.password)
    return ResponseEntity.ok(LoginResponse.of(accessToken, refreshToken));
  }

  @PostMapping("/refresh")
  fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<RefreshResponse> {
    val result = tokenCreator.accessTokenRefresh(request.refreshToken)
    return ResponseEntity.ok(RefreshResponse.of(result))
  }
}