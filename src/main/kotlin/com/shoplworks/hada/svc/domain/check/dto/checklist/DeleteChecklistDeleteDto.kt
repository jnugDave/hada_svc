package com.shoplworks.hada.svc.domain.check.dto.checklist

import io.swagger.v3.oas.annotations.media.Schema

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto
 * fileName : DeleteChecklistDeleteDto
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
class DeleteChecklistDeleteDto {
    @Schema(name = "DeleteChecklistDeleteDto.Request", description = "점검표 삭제 요청 DTO")
    class Request {

        @Schema(description = "점검표 ID", example = "[\"8578b4d4-0ce7-43c7-9d99-47641cc355ba\"]", required = true)
        var checklistIds: List<Long> = arrayListOf()
    }
}