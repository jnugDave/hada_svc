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
open class CheckCycleAttrVo {

    @Schema(description = "점검주기 타입(횟수지정, 시간지정 등)", implementation = CheckCycleType::class)
    open var checkCycleType: CheckCycleType? = null

    @Schema(description = "점검주기 단위", example = "1")
    open var checkCycleUnit: Int? = null

    @Schema(description = "점검횟수", example = "1")
    open var checkCount: Int? = null

    @Schema(description = "점검 시간(format: HH:mm)", example = "[\"12:00\",\"13:00\"]")
    open var checkTime: List<String>? = null

    @Schema(description = "점검일(1~31, 32(마지막날))", example = "[1,2,15]")
    open var checkDayOfMonth: List<Int>? = null

    @Schema(description = "점검요일(1:월 ~ 7:일)", example = "[0,2,6]")
    open var checkDayOfWeek: List<Int>? = null

    @Schema(description = "점검일&주([요일]/[주], 예: 0/1(첫번째 주 월요일), 5/7(마지막 주 토))", example = "[\"0/1\",\"5/7\"]")
    open var checkDayAndWeek: List<String>? = null

}