package com.shoplworks.hada.svc.domain.check.dto.checkTarget

import com.shoplworks.hada.svc.entity.CheckTargetChecklistMappingEntity
import com.shoplworks.hada.svc.entity.ChecklistEntity
import com.shoplworks.hada.svc.vo.CheckTargetAttrVo
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto.checkTarget
 * fileName : GetCheckTargetPageDto
 * author : dave
 * date : 2022/04/14
 * description : 최초등록
 */
class GetCheckTargetPageDto {

    @Hidden
    class Row{
        var checkTargetId: Long = 0

        lateinit var checkTargetName: String

        var lastCheckDt: LocalDateTime? = null
    }

    @Schema(name = "GetCheckTargetPageDto.Response", description = "점검 대상(장소) 조회 응답 DTO")
    class Response{
        @Schema(description = "점검대상 ID", example = "2")
        var checkTargetId: Long = 0

        @Schema(description = "점검대상 명", example = "8충 화장실")
        lateinit var checkTargetName: String

        @Schema(description = "점검표 리스트")
        lateinit var checklists: List<ChecklistDto>

        @Schema(description = "마지막 점검일")
        var lastCheckDt: LocalDateTime? = null

        fun of(row: Row, checklists: List<ChecklistDto>): Response {

            this.checkTargetId = row.checkTargetId
            this.checkTargetName = row.checkTargetName
            this.lastCheckDt = row.lastCheckDt

            this.checklists = checklists

            return this
        }
    }

    class ChecklistDto {

        @Schema(description = "점검대상 ID", example = "14")
        var checkTargetId: Long = 0

        @Schema(description = "점검표 ID", example = "13")
        var checklistId: Long = 0

        @Schema(description = "점검표 명", example = "화장실 청소")
        lateinit var checklistName: String
    }

}