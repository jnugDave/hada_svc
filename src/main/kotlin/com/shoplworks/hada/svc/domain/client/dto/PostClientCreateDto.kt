package com.shoplworks.hada.svc.domain.client.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * packageName : com.shoplworks.hada.svc.domain.client.dto
 * fileName : PostClientCreateDto
 * author : dave
 * date : 2022/03/29
 * description : 최초등록
 */
class PostClientCreateDto {

    @Schema(name = "PostClientCreateDto.Response", description = "클라인언트 생성 요청 dto")
    class Request {

        @NotEmpty
        @Schema(description = "클라이언트명", example = "하다컴퍼니", required = true)
        lateinit var clientName: String

        @Schema(description = "클라이언트 주소", example = "서울 강남구 도곡로 111")
        lateinit var clientAddress: String

        @Schema(description = "클라이언트 주소 상세", example = "8층 샤플앤컴퍼니")
        lateinit var clientAddressDetail: String

        @Schema(description = "우편번호", example = "06253")
        lateinit var zipCode: String

        @Schema(description = "국가코드", defaultValue = "KR")
        lateinit var countryCode: String

        @Schema(description = "타임존", defaultValue = "Asia/Seoul")
        lateinit var timezone: String

        @Schema(description = "활성여부", defaultValue = "true")
        var isActive: Boolean = true
    }

    @Schema(name = "PostClientCreateDto.Response", description = "클라이언트 생성 응답 dto")
    class Response(
        @Schema(description = "클라이언트 ID", example = "15", required = true)
        var clientId: Long = 0
    )
}