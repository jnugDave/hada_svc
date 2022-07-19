package com.shoplworks.hada.svc.domain.check.dto.checklist

import com.shoplworks.hada.svc.entity.ChecklistEntity
import com.shoplworks.hada.svc.vo.CheckFrequencyVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import com.shoplworks.hada.svc.vo.ChecklistItemVo
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto
 * fileName : GetChecklistDetailDto
 * author : dave
 * date : 2022/04/08
 * description : 최초등록
 */
class GetChecklistDetailDto {
    @Schema(name = "GetChecklistDetailDto.Response", description = "점검표 상세정보 응답 dto")
    class Response {
        @Schema(description = "클라이언트 ID")
        var clientId: Long = 0

        @Schema(description = "점검표 명", example = "화장실 청소")
        lateinit var checklistName: String

        @Schema(description = "점검표 속성")
        lateinit var checklistAttr: ChecklistAttrVo

        @Schema(description = "점검 항목")
        lateinit var checklistItems: List<ChecklistItemVo>

        @Schema(description = "점검 빈도")
        lateinit var checkFrequency: CheckFrequencyVo

        @Schema(description = "등록날짜")
        lateinit var regDt: LocalDateTime

        fun of(checklistEntity: ChecklistEntity): Response {
            this.checklistName = checklistEntity.checklistName
            //점겅표 속성
            this.checklistAttr = checklistEntity.checklistAttr
            //점검항목
            this.checklistItems = checklistEntity.checklistItemList.map {
                ChecklistItemVo().apply {
                    this.checklistItemId = it.checklistItemId
                    this.itemAttr = it.itemAttr
                    this.itemName = it.checklistItemName
                    this.itemOrder = it.itemOrder
                }
            }
            //점검빈도
            this.checkFrequency = CheckFrequencyVo().apply {
                this.checkCycle = checklistEntity.checkFrequency.checkCycle
                this.checkCycleAttr = checklistEntity.checkFrequency.checkCycleAttr
            }
            this.clientId = checklistEntity.client.clientId
            this.regDt = checklistEntity.regDt

            return this
        }
    }
}