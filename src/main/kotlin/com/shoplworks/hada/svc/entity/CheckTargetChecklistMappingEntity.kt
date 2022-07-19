package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import com.shoplworks.hada.svc.entity.common.idClass.CheckTargetChecklistIds
import javax.persistence.*

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : CheckTargetChecklistMappingEntity
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@Entity(name = "tb_check_target_checklist_mapping")
@IdClass(CheckTargetChecklistIds::class)
class CheckTargetChecklistMappingEntity protected constructor() : BaseTimeEntity() {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_target_id")
    lateinit var checkTarget: CheckTargetEntity

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    lateinit var checklist: ChecklistEntity

    @Column
    var itemOrder: Int = 0

    constructor(checkTarget: CheckTargetEntity, checklist: ChecklistEntity, itemOrder: Int): this() {
        this.checkTarget = checkTarget
        this.checklist = checklist
        this.itemOrder = itemOrder
    }

}