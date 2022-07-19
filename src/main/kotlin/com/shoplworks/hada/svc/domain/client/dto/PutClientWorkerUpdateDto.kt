package com.shoplworks.hada.svc.domain.client.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty

/**
 * packageName : com.shoplworks.hada.svc.domain.client.dto
 * fileName : PutClientWorkerDto
 * author : dave
 * date : 2022/04/18
 * description : 최초등록
 */
class PutClientWorkerUpdateDto {

    @Schema(name = "PutClientWorkerUpdateDto.Request", description = "클라이언트 작업자 수정 요청 DTO")
    class Request {

        @NotEmpty
        @Schema(description = "유저 이름", example = "SU HUA", required = true)
        lateinit var workerName: String

        @NotEmpty
        @Schema(description = "휴대폰 번호", example = "01012341234", required = true)
        lateinit var workerPhone: String

        @Schema(description = "아메일", example = "nana@shoplworks.com")
        var workerEmail: String? = null

        @Schema(description = "메모", example = "구내 식당 청소 담당")
        var memo: String? = null
    }
}