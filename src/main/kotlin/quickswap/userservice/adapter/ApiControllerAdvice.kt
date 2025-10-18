package quickswap.userservice.adapter

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import quickswap.userservice.domain.user.DuplicateEmailException
import java.time.LocalDateTime

@ControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {

  @ExceptionHandler(DuplicateEmailException::class)
  fun emailExceptionHandler(exception: DuplicateEmailException): ProblemDetail {
    logger.error("DuplicateEmailException 발생: ${exception.message}", exception)
    return getProblemDetail(HttpStatus.CONFLICT, exception)
  }

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleValidationException(exception: IllegalArgumentException): ProblemDetail {
    logger.warn("IllegalArgumentException 발생: ${exception.message}", exception)
    return getProblemDetail(HttpStatus.BAD_REQUEST, exception)
  }

  @ExceptionHandler(Exception::class)
  fun handleException(exception: Exception): ProblemDetail {
    logger.error("예상치 못한 예외 발생: ${exception.message}", exception)
    return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception)
  }

  companion object {
    private fun getProblemDetail(status: HttpStatus, exception: Exception): ProblemDetail {
      val problemDetail = ProblemDetail.forStatusAndDetail(status, exception.message)

      problemDetail.setProperty("timestamp", LocalDateTime.now())
      problemDetail.setProperty("exception", exception.javaClass.simpleName)

      return problemDetail
    }
  }

  override fun handleHttpMessageNotReadable(
    ex: HttpMessageNotReadableException,
    headers: HttpHeaders,
    status: HttpStatusCode,
    request: WebRequest
  ): ResponseEntity<Any?>? {
    logger.error("요청 본문 읽기 실패: ${ex.message}", ex)

    val errorMessage = ex.cause?.cause?.message ?: "잘못된 요청 형식입니다"

    val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage)
    problemDetail.setProperty("timestamp", LocalDateTime.now())
    problemDetail.setProperty("exception", ex.javaClass.simpleName)

    return ResponseEntity.badRequest().body(problemDetail)
  }
}