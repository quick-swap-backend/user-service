package quickswap.userservice.domain.user

data class Password(val value: String) {

  companion object {
    private val PASSWORD_REGEX = "^\\S{6,}$".toRegex()
  }

  init {
    require(value.matches(PASSWORD_REGEX)) {
      "비밀번호는 최소 6자 이상이어야 합니다 (공백 불가)"
    }
  }
}
