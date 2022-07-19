package com.shoplworks.hada.svc.domain.check.dto.checklist

import com.shoplworks.hada.svc.entity.CheckFrequencyEntity
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto
 * fileName : GetChecklistPageDto
 * author : dave
 * date : 2022/04/08
 * description : 최초등록
 */
class GetChecklistPageDto {

    @Schema(name = "GetChecklistPageDto.Response", description = "점검표 페이징 응답 dto")
    class Response {
        @Schema(description = "점검표 ID")
        var checklistId: Long = 0

        @Schema(description = "점검표 ID")
        lateinit var checklistName: String

        @Schema(description = "점검빈도")
        lateinit var checkFrequency: String

        @Schema(description = "사용중인 장소 갯수")
        var checkTargetCount: Long = 0

        fun of(value: Value): Response {
            this.checklistName = value.checklistName
            this.checklistId = value.checklistId
            this.checkTargetCount = value.checkTargetCount.toLong()
            this.checkFrequency = "작업필요"

            return this
        }
    }

    @Hidden
    class Value {
        @Schema(description = "점검표 ID")
        var checklistId: Long = 0

        @Schema(description = "점검표 ID")
        lateinit var checklistName: String

        @Schema(description = "점검빈도 엔티티")
        lateinit var checkFrequency: CheckFrequencyEntity

        @Schema(description = "사용중인 장소 갯수")
        var checkTargetCount: Int = 0
    }
}