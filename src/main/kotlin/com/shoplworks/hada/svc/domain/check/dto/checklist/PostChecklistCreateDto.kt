package com.shoplworks.hada.svc.domain.check.dto.checklist

import com.shoplworks.hada.svc.vo.CheckFrequencyVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import com.shoplworks.hada.svc.vo.ChecklistItemVo
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.domain.client.dto
 * fileName : PostChecklistCreateDto
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
class PostChecklistCreateDto {

    @Schema(name = "PostChecklistCreateDto.Request", description = "점검표 생성 요청 dto")
    class Request {
        @NotEmpty
        @Schema(description = "점검표 명", example = "화장실 청소", required = true)
        lateinit var checklistName: String

        @NotNull
        @Schema(description = "점검 항목", required = true)
        lateinit var checklistItems: List<ChecklistItemVo>

        @NotNull
        @Schema(description = "점검 빈도", required = true)
        lateinit var checkFrequency: CheckFrequencyVo

        @NotNull
        @Schema(description = "점검표 속성", required = true)
        lateinit var checklistAttr: ChecklistAttrVo
    }

    @Schema(name = "PostChecklistCreateDto.Response", description = "점검표 생성 응답 dto")
    class Response(
        @Schema(description = "점검표 ID", example = "11")
        var checkListId: Long = 0
    )
}