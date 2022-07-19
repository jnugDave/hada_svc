package com.shoplworks.hada.svc.common.exception

import com.shoplworks.hada.svc.common.enums.ErrorCode

/**
 * packageName : com.shoplworks.hada.svc.common.exception
 * fileName : CustomException
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
class CustomException(
    val errorCode: ErrorCode,
    override var message: String? = null
): RuntimeException(message) {

}