package com.shoplworks.hada.svc.domain.client.dto

import com.shoplworks.hada.svc.common.enums.UserType
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty

/**
 * packageName : com.shoplworks.hada.svc.domain.client.dto
 * fileName : PostClientUserOperatorManagerDto
 * author : dave
 * date : 2022/04/15
 * description : 최초등록
 */
class PostClientWorkerCreateDto {

    @Schema(name = "PostClientUserWorkerCreateDto.Request", description = "클라이언트 작업자 생성 요청 DTO")
    class Request {
        @NotEmpty
        @Schema(description = "작업자 이름", example = "su hua", required = true)
        lateinit var userName: String

        @NotEmpty
        @Schema(description = "작업자 휴대폰 번호", example = "01012341234", required = true)
        lateinit var userPhone: String

        @Schema(description = "작업자 이메일", example = "abc@shoplworks.com", required = false)
        var userEmail: String? = null

        @Schema(description = "메모", example = "구내 식당 청소 담당", required = false)
        var memo: String? = null
    }

    @Schema(name = "PostClientUserWorkerCreateDto.Response", description = "클라이언트 작업자 생성 응답 DTO")
    class Response(
        @Schema(description = "클리이언트 유저 Id", example = "5", required = true)
        var clientUserId: Long = 0
    )
}