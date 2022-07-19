package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import com.shoplworks.hada.svc.entity.common.converter.ModelConverter
import com.shoplworks.hada.svc.vo.CheckTargetAttrVo
import com.shoplworks.hada.svc.vo.CheckTargetChecklistMappingVo
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.domain.Sort
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : CheckTargetEntity
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@Entity(name = "tb_check_target")
class CheckTargetEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val checkTargetId: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    lateinit var client: ClientEntity
        protected set

    @NotNull
    @Column(length = 200, nullable = false)
    lateinit var checkTargetName: String
        protected set

    @Column(columnDefinition = "json", updatable = false, nullable = false)
    @Convert(converter = ModelConverter.CheckTargetAttrConverter::class)
    lateinit var checkTargetAttr: CheckTargetAttrVo
        protected set

    @Column @ColumnDefault("0")
    var isDelete: Boolean = false
        protected set

    @Column(nullable = true)
    var lastCheckDt: LocalDateTime? = null
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "checklist", cascade = [CascadeType.ALL], orphanRemoval = true)
    val checkTargetChecklistMappingList: MutableList<CheckTargetChecklistMappingEntity> = mutableListOf()


    constructor(client: ClientEntity, checkTargetName: String, checkTargetAttr: CheckTargetAttrVo, checklistList: List<ChecklistEntity>): this() {
        this.client = client
        this.checkTargetName = checkTargetName
        this.checkTargetAttr = checkTargetAttr

        for(i in checklistList.indices) {
            this.checkTargetChecklistMappingList.add(
                CheckTargetChecklistMappingEntity(this, checklistList[i], i)
            )
        }
    }
}