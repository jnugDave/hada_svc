package com.shoplworks.hada.svc.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * packageName : com.shoplworks.hada.svc.domain.member.dto
 * fileName : GetMemberInfoDto
 * author : dave
 * date : 2022/03/28
 * description : 최초등록
 */
class GetMemberInfoDto {

    @Schema(name = "GetMemberInfoDto.Response", description = "멤버 정보 반환 dto")
    class Response {

        @NotEmpty
        @Email
        @Schema(description = "유저 아이디", example = "11")
        var memberId: Long? = null

        @NotEmpty
        @Email
        @Schema(description = "유저 이메일", example = "inwook94@naver.com")
        var email: String? = null

        @NotEmpty
        @Schema(description = "유저 휴대폰 번호", example = "01086291751")
        var phone: String? = null

        @NotEmpty
        @Size(min = 2, max = 10, message = "닉네임은 2~10자 이어야 합니다.")
        @Schema(description = "유저 닉네임", example = "인눅")
        var nickname: String? = null
    }
}