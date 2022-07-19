package com.shoplworks.hada.svc.vo

import com.shoplworks.hada.svc.common.enums.CheckCycle
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty

/**
 * packageName : com.shoplworks.hada.svc.vo
 * fileName : CheckFrequencyVo
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@Schema(name = "CheckFrequencyVo", description = "점검 빈도 VO")
open class CheckFrequencyVo {

    @Schema(description = "점검 주기", example= "DAILY", implementation = CheckCycle::class)
    lateinit var checkCycle: CheckCycle

    @Schema(description = "점검 주기 데이터", implementation = CheckCycleAttrVo::class)
    lateinit var checkCycleAttr: CheckCycleAttrVo
}