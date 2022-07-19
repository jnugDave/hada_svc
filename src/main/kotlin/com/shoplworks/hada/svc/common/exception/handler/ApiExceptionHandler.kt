package com.shoplworks.hada.svc.common.exception.handler

import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.http.ApiResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * packageName : com.shoplworks.hada.svc.common.exception.handler
 * fileName : ApiExceptionHandler
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@RestControllerAdvice
class ApiExceptionHandler {

    /**
     * api 로직상 예외가 생겼을 경우 예외처리
     */
    @ExceptionHandler(
        CustomException::class
    )
    fun customException(e: CustomException): ResponseEntity<ApiResult<*>> {
        println("message: " + e.message)
        return ResponseEntity
            .status(e.errorCode.httpStatus)
            .body(ApiResult(null).withException(e))
    }
    /**
        @RequestBody로 받은 값에 대해 검증에 실패했을 경우 예외처리
    */
    @ExceptionHandler(
        MethodArgumentNotValidException::class
    )
    fun requestValidationException(e: MethodArgumentNotValidException): ResponseEntity<ApiResult<*>> {
        return ResponseEntity
            .status(400) //validation 예외는 항상 400
            .body(ApiResult(null).withValidException(e))
    }

}