package com.topjava.graduation.web

import com.topjava.graduation.util.ValidationUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler {
    companion object {
        val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun defaultErrorHandler(req: HttpServletRequest, e: Exception): ModelAndView {
        logger.error("Exception at request ${req.requestURL}", e)
        val rootCause = ValidationUtil.getRootCause(e);

        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        val mav = ModelAndView("exception",
                mapOf("exception" to rootCause,
                        "message" to ValidationUtil.getMessage(rootCause),
                        "status" to httpStatus))
        mav.status = httpStatus

        val authorizedUser = SecurityUtil.safeGet()
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.userTo)
        }
        return mav
    }
}