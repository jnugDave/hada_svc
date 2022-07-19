package com.shoplworks.hada.svc.domain.check.dto.checkTarget

import com.shoplworks.hada.svc.vo.CheckFrequencyVo
import com.shoplworks.hada.svc.vo.CheckTargetAttrVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import com.shoplworks.hada.svc.vo.ChecklistItemVo
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.domain.check.dto.checkTarget
 * fileName : PostCheckTargetCreateDto
 * author : dave
 * date : 2022/04/14
 * description : 최초등록
 */
class PostCheckTargetCreateDto {

    @Schema(name = "PostCheckTargetCreateDto.Request", description = "점검대상 생성 요청 dto")
    class Request {
        @NotEmpty
        @Schema(description = "점검대상 명", example = "8층 화장실", required = true)
        lateinit var checkTargetName: String

        @NotNull
        @Schema(description = "점검 항목", required = true)
        lateinit var checkTargetAttr: CheckTargetAttrVo

        @NotNull
        @Schema(description = "연결된 점검표 리스트")
        var checklistMappingList: List<ChecklistMappingDto> = listOf()
    }

    @Schema(name = "PostCheckTargetCreateDto.Response", description = "점검대상 생성 응답 dto")
    class Response(
        @Schema(description = "점검대상 ID", example = "11")
        var checkTargetId: Long = 0
    )

    @Schema
    class ChecklistMappingDto(
        @Schema(description = "점검표 ID", example = "11")
        var checklistId: Long = 0,

        @Schema(description = "점검대상에서 점검표 순서", example = "0")
        var itemOrder: Int = 0
    )

}