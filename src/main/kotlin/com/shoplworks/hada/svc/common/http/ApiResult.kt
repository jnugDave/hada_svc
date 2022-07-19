package com.shoplworks.hada.svc.common.http

import com.shoplworks.hada.svc.common.exception.CustomException
import org.springframework.web.bind.MethodArgumentNotValidException
import java.io.Serializable
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.common.http
 * fileName : ApiResult
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
class ApiResult<T>(
    val result: T? = null,

    var success: Boolean = true,

    var status: Int = 200,

    var message: String? = "success",

) : Serializable {

    val timestamp: Date = Date()


    fun withException(e: CustomException?): ApiResult<T> {
        e?.let {
            success = false
            status = it.errorCode.status
            message = it.message ?: it.errorCode.message // CustomException에 메세지를 직접 지정해주면 해당 메세지가 반영
        }
        return this
    }

    fun withValidException(e: MethodArgumentNotValidException?): ApiResult<T> {
        e?.let {

            val stringBuilder = StringBuilder()

            //에러 메세지를 합쳐준다
            for(fieldError in it.fieldErrors){
                stringBuilder.append(fieldError.field).append(":")
                stringBuilder.append(fieldError.defaultMessage).append(", ")
            }

            success = false
            status = 400
            message = stringBuilder.toString()
        }
        return this
    }
}