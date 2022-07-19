package com.shoplworks.hada.svc.domain.client.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty

/**
 * packageName : com.shoplworks.hada.svc.domain.client.dto
 * fileName : GetClientUserListPageDto
 * author : dave
 * date : 2022/04/16
 * description : 최초등록
 */
class GetClientWorkerListPageDto {

    @Schema(name = "GetClientWorkerListPageDto.Response", description = "클라이언트 작업자 페이지 응답 dto")
    class Response {

        @Schema(description = "작업자 ID", example = "1")
        var clientUserId: Long = 0

        @Schema(description = "작업자 ID", example = "SU HUA")
        lateinit var workerName: String

        @Schema(description = "작업자 휴대폰 번호", example = "01012345678")
        lateinit var workerPhone: String

        @Schema(description = "작업자 이메일", example = "abc@shoplworks.com")
        var workerEmail: String? = null

        @Schema(description = "메모", example = "남자 화장실 청소 담당")
        var memo: String? = null
    }
}