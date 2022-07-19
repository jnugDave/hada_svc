package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.common.enums.CheckCycle
import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import com.shoplworks.hada.svc.entity.common.converter.ModelConverter
import com.shoplworks.hada.svc.vo.CheckCycleAttrVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import com.shoplworks.hada.svc.vo.ChecklistItemVo
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.domain.Sort
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : ChecklistEntity
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@Entity(name = "tb_checklist")
class ChecklistEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val checklistId: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    lateinit var client: ClientEntity
        protected set

    @NotNull
    @Column(length = 200)
    lateinit var checklistName: String
        protected set

    @Column
    @ColumnDefault("0")
    var checkTargetCount: Int = 0
        protected set

    @Column(columnDefinition = "json", updatable = false)
    @Convert(converter = ModelConverter.ChecklistAttrConverter::class)
    lateinit var checklistAttr: ChecklistAttrVo
        protected set

    @Column @ColumnDefault("0")
    var isDelete: Boolean = false
        protected set

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "check_frequency_id", nullable = false)
    lateinit var checkFrequency: CheckFrequencyEntity
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "checklist", cascade = [CascadeType.ALL], orphanRemoval = true)
    var checklistItemList: MutableList<ChecklistItemEntity> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "checklist", cascade = [CascadeType.ALL], orphanRemoval = true)
    var checkTargetChecklistMappingList: MutableList<CheckTargetChecklistMappingEntity> = mutableListOf()

    /** 엔티티 생성 */
    constructor(client: ClientEntity, checklistName: String, checklistAttr: ChecklistAttrVo, checkCycle: CheckCycle, checkCycleAttr: CheckCycleAttrVo,
    checklistItemList: List<ChecklistItemVo>): this() {
        this.client = client
        this.checklistName = checklistName
        this.checklistAttr = checklistAttr
        this.isDelete = false
        //점검 빈도 생성 및 저장
        this.checkFrequency = CheckFrequencyEntity(
            checkCycle,
            checkCycleAttr
        )
        //점검 항목 생성 및 저장
        checklistItemList.forEach { checklist ->
            this.checklistItemList.add(
                ChecklistItemEntity(this, checklist.itemName, checklist.itemOrder, checklist.itemAttr)
            )
        }
    }

    /** 비즈니스 로직 */
    fun delete() {
        this.isDelete = true
        this.delDt = LocalDateTime.now()
        //모든 매핑 테이블의 데이터도 함께 삭제
        checkTargetChecklistMappingList.removeAll(this.checkTargetChecklistMappingList)
    }

    fun update(checklistAttr: ChecklistAttrVo, checklistName: String, checkCycle: CheckCycle,
               checkCycleAttr: CheckCycleAttrVo, checklistItemList: List<ChecklistItemVo>) {
        if(this.isDelete) {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        this.checklistAttr = checklistAttr
        this.checklistName = checklistName
        //점검 빈도 업데이트
        this.checkFrequency.update(checkCycle, checkCycleAttr)
        //기존 점검 항목 모두 삭제
        this.checklistItemList.removeAll(this.checklistItemList)
        //점검 항목 새로 등록
        checklistItemList.forEach { checklistItem ->
            this.checklistItemList.add(
                ChecklistItemEntity(this, checklistItem.itemName, checklistItem.itemOrder, checklistItem.itemAttr)
            )
        }
    }
}