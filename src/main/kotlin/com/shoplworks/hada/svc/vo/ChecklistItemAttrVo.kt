package com.shoplworks.hada.svc.vo

import com.shoplworks.hada.svc.common.enums.OptionalRequired
import io.swagger.v3.oas.annotations.media.Schema

/**
 * packageName : com.shoplworks.hada.vo
 * fileName : CheckTargetAttributeVo
 * author : kevin
 * date : 2022/03/03
 * description : 점검표 속성 값
 */

@Schema(name = "ChecklistItemAttrVo", description = "점검항목 속성 VO")
class ChecklistItemAttrVo {

    @Schema(description = "사진 첨부 옵션", implementation = OptionalRequired::class)
    lateinit var attachPhoto: OptionalRequired
}