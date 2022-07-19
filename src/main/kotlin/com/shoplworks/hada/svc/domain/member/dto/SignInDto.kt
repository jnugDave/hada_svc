package com.shoplworks.hada.svc.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * packageName : com.shoplworks.hada.svc.domain.member.dto
 * fileName : SignInReq
 * author : dave
 * date : 2022/07/16
 * description : 최초등록
 */
@Schema(name = "SignInReq.Request", description = "유저 일반 이메일 로그인 요청 dto")
class SignInDto {

    class Request {
        @NotEmpty
        @Email
        @Schema(description = "유저 이메일", example = "inwook94@naver.com", required = true)
        lateinit var email: String

        @NotEmpty
        @Size(min = 2, max = 15, message = "비밀번호는 2~15자 이어야 합니다.")
        @Schema(description = "유저 비밀번호", example = "wjddlsdnr12!", required = true)
        lateinit var password: String
    }

    class Response {
        var memberId: Long? = null
        var token: String? = null
    }


}