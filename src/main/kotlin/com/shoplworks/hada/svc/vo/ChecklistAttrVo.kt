package com.shoplworks.hada.svc.vo

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty

/**
 * packageName : com.shoplworks.hada.vo
 * fileName : CheckTargetAttributeVo
 * author : kevin
 * date : 2022/03/03
 * description : 점검표 속성 값
 */

@Schema(name = "ChecklistAttrVo", description = "점검표 속성 VO")
class ChecklistAttrVo {
    @Schema(description = "안내사항", example = "열심히 해주세요!")
    var notification: String? = null
}