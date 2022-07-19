package com.shoplworks.hada.svc.common.enums

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

/**
 * packageName : com.shoplworks.hada.svc.common.enums
 * fileName : ErrorCode
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
enum class ErrorCode(val httpStatus: HttpStatus, val status: Int, val message: String) {

    /** 400 */
    BAD_ARGUMENT(BAD_REQUEST, 400001, "bad argument"),
    INVALID_PARAMETER(BAD_REQUEST, 400002, "bad argument"),

    /** 401 */
    MEMBER_UNAUTHORIZED(UNAUTHORIZED, 400001, "unauthorized member"),
    CLIENT_USER_NOT_AUTHORIZED(UNAUTHORIZED, 400002, "client user not authorized"),

    /** 403 */
    FORBIDDEN_RESOURCE(FORBIDDEN, 403001, "forbidden resource"),

    /** 404 */
    USER_NOT_FOUND(NOT_FOUND, 404001, "user not found"),
    RESOURCE_NOT_FOUND(NOT_FOUND, 404002, "resource not found"),
    CLIENT_USER_NOT_FOUND(NOT_FOUND, 404003, "client user not found"),

    /** 409 */
    USER_ALREADY_EXIST(CONFLICT, 409001, "user already exist"),
    RESOURCE_ALREADY_EXIST(CONFLICT, 409002, "resource already exist"),
    RESOURCE_NAME_ALREADY_EXIST(CONFLICT, 409003, "resource name already exist"),

    /** 500 */
    INTERNAL_SERVER_ERR(INTERNAL_SERVER_ERROR, 500001, "internal server error")

}