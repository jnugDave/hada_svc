package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.common.enums.CheckCycle
import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import com.shoplworks.hada.svc.entity.common.converter.ModelConverter
import com.shoplworks.hada.svc.vo.CheckCycleAttrVo
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : CheckFrequencyEntity
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@Entity(name = "tb_check_frequency")
class CheckFrequencyEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val checkFrequencyId: Long = 0

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var checkCycle: CheckCycle
        protected set

    @Column(columnDefinition = "json", nullable = false)
    @Convert(converter = ModelConverter.CheckCycleAttrConverter::class)
    lateinit var checkCycleAttr: CheckCycleAttrVo
        protected set

    constructor(checkCycle: CheckCycle, checkCycleAttr: CheckCycleAttrVo): this() {
        this.checkCycle = checkCycle
        this.checkCycleAttr = checkCycleAttr
    }

    fun update(checkCycle: CheckCycle, checkCycleAttr: CheckCycleAttrVo) {
        this.checkCycle = checkCycle
        this.checkCycleAttr = checkCycleAttr
    }
}