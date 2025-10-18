package quickswap.userservice.adapter

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import quickswap.userservice.domain.user.DuplicateEmailException
import java.time.LocalDateTime

@ControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {

  @ExceptionHandler(DuplicateEmailException::class)
  fun emailExceptionHandler(exception: DuplicateEmailException): ProblemDetail {
    return getProblemDetail(HttpStatus.CONFLICT, exception)
  }

  companion object {
    private fun getProblemDetail(status: HttpStatus, exception: Exception): ProblemDetail {
      val problemDetail = ProblemDetail.forStatusAndDetail(status, exception.message)

      problemDetail.setProperty("timestamp", LocalDateTime.now())
      problemDetail.setProperty("exception", exception.javaClass.getSimpleName())

      return problemDetail
    }
  }

  @ExceptionHandler(Exception::class)
  fun handleException(exception: Exception): ProblemDetail {
    return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception)
  }

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleValidationException(exception: IllegalArgumentException): ProblemDetail {
    return getProblemDetail(HttpStatus.BAD_REQUEST, exception)
  }
}