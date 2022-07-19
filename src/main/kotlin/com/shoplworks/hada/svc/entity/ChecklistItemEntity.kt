package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import com.shoplworks.hada.svc.entity.common.converter.ModelConverter
import com.shoplworks.hada.svc.vo.ChecklistItemAttrVo
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.entity.common
 * fileName : ChecklistItemEntity
 * author : dave
 * date : 2022/04/05
 * description : 최초등록
 */
@Entity(name = "tb_checklist_item")
class ChecklistItemEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var checklistItemId: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    lateinit var checklist: ChecklistEntity
        protected set

    @NotNull
    @Column(length = 200, nullable = false)
    lateinit var checklistItemName: String
        protected set

    @OrderColumn
    var itemOrder: Int = 0
        protected set

    @Column(columnDefinition = "json", updatable = false)
    @Convert(converter = ModelConverter.ChecklistItemAttrConverter::class)
    lateinit var itemAttr: ChecklistItemAttrVo
        protected set

    constructor(checklist: ChecklistEntity, checklistItemName: String, itemOrder: Int, itemAttr: ChecklistItemAttrVo): this() {
        this.checklist = checklist
        this.checklistItemName = checklistItemName
        this.itemOrder = itemOrder
        this.itemAttr = itemAttr
    }
}