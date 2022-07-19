package com.shoplworks.hada.svc.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.*

/**
 * packageName : com.shoplworks.hada.svc.domain.member.dto
 * fileName : PostJoinMemberDto
 * author : dave
 * date : 2022/03/24
 * description : 최초등록
 */
class SignUpDto {

    @Schema(name = "SignUpReq.Request", description = "유저 일반 이메일 회원가입 요청 dto")
    class Request {

        @NotEmpty
        @Email
        @Schema(description = "유저 이메일", example = "inwook94@naver.com", required = true)
        lateinit var email: String

        @NotEmpty
        @Size(min = 2, max = 15, message = "비밀번호는 2~15자 이어야 합니다.")
        @Schema(description = "유저 비밀번호", example = "wjddlsdnr12!", required = true)
        lateinit var password: String

        @NotEmpty
        @Schema(description = "유저 휴대폰 번호", example = "01086291751", required = true)
        lateinit var phone: String

        @NotEmpty
        @Size(min = 2, max = 10, message = "닉네임은 2~10자 이어야 합니다.")
        @Schema(description = "유저 닉네임", example = "인눅", required = true)
        lateinit var nickname: String
    }

    @Schema(name = "SignUpReq.Response", description = "유저 일반 이메일 회원가입 응답 dto")
    class Response {

        @Schema(description = "유저 이메일", example = "inwook94@naver.com")
        lateinit var memberId: String

        @Schema(description = "유저 이메일", example = "inwook94@naver.com")
        lateinit var email: String

        @Schema(description = "유저 휴대폰 번호", example = "01086291751")
        lateinit var phone: String

        @Schema(description = "유저 닉네임", example = "인눅")
        lateinit var nickname: String
    }
}