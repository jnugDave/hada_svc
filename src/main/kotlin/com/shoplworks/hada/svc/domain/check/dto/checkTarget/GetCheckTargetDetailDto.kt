package com.shoplworks.hada.svc.domain.check.dto.checkTarget

import com.shoplworks.hada.svc.entity.CheckTargetEntity
import com.shoplworks.hada.svc.entity.ChecklistEntity
import com.shoplworks.hada.svc.vo.CheckTargetAttrVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto.checkTarget
 * fileName : GetCheckTargetDetailDto
 * author : dave
 * date : 2022/04/14
 * description : 최초등록
 */
class GetCheckTargetDetailDto {

    class Response {

        @Schema(description = "점검대상 ID", example = "12")
        var checkTargetId: Long = 0

        @Schema(description = "클라이언트 ID", example = "11")
        var clientId: Long = 0

        @Schema(description = "점검대상 명", example = "8충 화장실")
        lateinit var checkTargetName: String

        @Schema(description = "점검표 리스트")
        lateinit var checklists: List<ChecklistDto>

        @Schema(description = "마지막 점검일")
        var lastCheckDt: LocalDateTime? = null

        @Schema(description = "점검대상 속성")
        lateinit var checkTargetAttr: CheckTargetAttrVo

        @Schema(description = "생성일")
        lateinit var regDt: LocalDateTime

        fun of(
            checkTargetEntity: CheckTargetEntity,
            checklists: List<ChecklistEntity>
        ): Response {
            this.checkTargetId = checkTargetEntity.checkTargetId
            this.clientId = checkTargetEntity.client.clientId
            this.lastCheckDt = checkTargetEntity.lastCheckDt
            this.checkTargetAttr = checkTargetEntity.checkTargetAttr
            this.checkTargetName = checkTargetEntity.checkTargetName
            this.regDt = checkTargetEntity.regDt

            val checklists = checklists.map { checklist ->
                ChecklistDto().apply {
                    this.checklistId = checklist.checklistId
                    this.checklistName = checklist.checklistName
                    this.checklistAttr = checklist.checklistAttr
                    this.regDt = checklist.regDt
                }
            }

            this.checklists = checklists

            return this
        }
    }

    class ChecklistDto {
//        @Schema(description = "클라이언트 ID", example = "12")
//        var clientId: Long = 0

        @Schema(description = "점검표 ID", example = "13")
        var checklistId: Long = 0

        @Schema(description = "점검표 명", example = "화장실 청소")
        lateinit var checklistName: String

        @Schema(description = "점검표 속성")
        lateinit var checklistAttr: ChecklistAttrVo

        @Schema(description = "생성일")
        lateinit var regDt: LocalDateTime
    }
}