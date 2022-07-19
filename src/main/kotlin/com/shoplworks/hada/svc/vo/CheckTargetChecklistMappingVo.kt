package com.shoplworks.hada.svc.vo

import com.shoplworks.hada.svc.common.enums.CheckCycleType
import io.swagger.v3.oas.annotations.media.Schema

/**
 * packageName : com.shoplworks.hada.vo
 * fileName : CheckTimeVo
 * author : kevin
 * date : 2022/03/03
 * description : 점검 시간
 */

@Schema(name = "CheckCycleAttrVo", description = "점검빈도 속성 VO")
open class CheckTargetChecklistMappingVo {

    @Schema(description = "매핑된 점검표 ID")
    open var checklistId: Long? = null

    @Schema(description = "매핑된 점검표 순서")
    open var itemOrder: Int? = null
}