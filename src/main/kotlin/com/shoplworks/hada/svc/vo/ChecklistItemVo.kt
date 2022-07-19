package com.shoplworks.hada.svc.vo

import com.shoplworks.hada.svc.common.enums.CheckItemType
import io.swagger.v3.oas.annotations.media.Schema

/**
 * packageName : com.shoplworks.hada.vo
 * fileName : CheckTimeVo
 * author : kevin
 * date : 2022/03/03
 * description : 점검 시간
 */

@Schema(name = "ChecklistItemVo", description = "점검항목 VO")
class ChecklistItemVo {

    @Schema(description = "점검표 항목 ID", example = "1")
    var checklistItemId: Long = 0

//    @Schema(description = "점검항목 타입", implementation = CheckItemType::class)
//    lateinit var itemType: CheckItemType

    @Schema(description = "점검항목 명", example = "휴지통은 비웠나요?")
    lateinit var itemName: String

    @Schema(description = "점검항목 순서(숫자가 낮을 수록 높음)", example = "0")
    var itemOrder: Int = 0

    @Schema(description = "점검항목 속성", implementation = ChecklistItemAttrVo::class)
    lateinit var itemAttr: ChecklistItemAttrVo

}