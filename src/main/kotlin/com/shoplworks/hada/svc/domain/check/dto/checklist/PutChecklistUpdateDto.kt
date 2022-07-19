package com.shoplworks.hada.svc.domain.check.dto.checklist

import com.shoplworks.hada.svc.vo.CheckFrequencyVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import com.shoplworks.hada.svc.vo.ChecklistItemVo
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto
 * fileName : PutChecklistUpdateDto
 * author : dave
 * date : 2022/04/05
 * description : 최초등록
 */
@Schema(name = "PutChecklistUpdateDto", description = "점검표 업데이트 dto")
class PutChecklistUpdateDto {
    @Schema(name = "PutChecklistUpdateDto.Request", description = "점검표 업데이트 요청 dto")
    class Request {

        @NotEmpty
        @Schema(description = "점검표 명", example = "화장실 청소", required = true)
        lateinit var checklistName: String

        @NotNull
        @Schema(description = "점검빈도")
        lateinit var checkFrequency: CheckFrequencyVo

        @NotNull
        @Schema(description = "점검 항목")
        lateinit var checklistItems: List<ChecklistItemVo>

        @NotNull
        @Schema(description = "점검표 속성")
        lateinit var checklistAttr: ChecklistAttrVo
    }
}