package com.topjava.graduation.web

import com.topjava.graduation.util.ErrorInfo
import com.topjava.graduation.util.ErrorType
import com.topjava.graduation.util.IllegalRequestDataException
import com.topjava.graduation.util.NotFoundException
import com.topjava.graduation.util.ValidationUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice(annotations = [RestController::class])
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
class ExceptionInfoHandler {

    companion object {
        val logger = LoggerFactory.getLogger(ExceptionInfoHandler::class.java)

        const val EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail"
        const val EXCEPTION_DUPLICATE_DATETIME = "exception.meal.duplicateDateTime"

        val CONSTRAINS_I18N_MAP = mapOf(
                "users_unique_email_idx" to EXCEPTION_DUPLICATE_EMAIL,
                "meals_unique_user_datetime_idx" to EXCEPTION_DUPLICATE_DATETIME)

        fun logAndGetErrorInfo(req: HttpServletRequest, e: Exception, logException: Boolean, errorType: ErrorType, vararg details: String): ErrorInfo {
            val rootCause = ValidationUtil.getRootCause(e)
            if (logException) {
                logger.error("$errorType at request ${req.requestURL}", rootCause)
            } else {
                logger.warn("$errorType at request ${req.requestURL}: $rootCause")
            }
            return ErrorInfo(req.requestURL.toString(), errorType,
                    (if (details.isNotEmpty()) details else arrayOf(ValidationUtil.getMessage(rootCause))).toList())
        }
    }

    @Autowired
    private lateinit var messageUtil: MessageUtil

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException::class)
    fun handleError(req: HttpServletRequest, e: NotFoundException): ErrorInfo {
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_NOT_FOUND)
    }

    @ResponseStatus(value = HttpStatus.CONFLICT) // 409
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun conflict(req: HttpServletRequest, e: DataIntegrityViolationException): ErrorInfo {
        val rootMsg = ValidationUtil.getRootCause(e).message
        if (rootMsg != null) {
            val lowerCaseMsg = rootMsg.toLowerCase()
            val entry = CONSTRAINS_I18N_MAP.entries.stream()
                    .filter { lowerCaseMsg.contains(it.key) }
                    .findAny()
            if (entry.isPresent) {
                return logAndGetErrorInfo(req, e, false, ErrorType.VALIDATION_ERROR, messageUtil.getMessage(entry.get().value))
            }
        }
        return logAndGetErrorInfo(req, e, true, ErrorType.DATA_ERROR)
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(BindException::class, MethodArgumentNotValidException::class)
    fun bindValidationError(req: HttpServletRequest?, e: java.lang.Exception): ErrorInfo? {
        val result = if (e is BindException) e.bindingResult else (e as MethodArgumentNotValidException).bindingResult
        val details = result.fieldErrors
                .map { fe: FieldError? -> messageUtil.getMessage(fe!!) }
                .toString()
        return logAndGetErrorInfo(req!!, e, false, ErrorType.VALIDATION_ERROR, details)
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(IllegalRequestDataException::class, MethodArgumentTypeMismatchException::class, HttpMessageNotReadableException::class)
    fun illegalRequestDataError(req: HttpServletRequest?, e: java.lang.Exception?): ErrorInfo? {
        return logAndGetErrorInfo(req!!, e!!, false, ErrorType.VALIDATION_ERROR)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(java.lang.Exception::class)
    fun handleError(req: HttpServletRequest?, e: java.lang.Exception?): ErrorInfo? {
        return logAndGetErrorInfo(req!!, e!!, true, ErrorType.APP_ERROR)
    }
}