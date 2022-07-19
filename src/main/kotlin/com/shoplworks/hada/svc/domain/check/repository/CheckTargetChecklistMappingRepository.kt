package com.shoplworks.hada.svc.domain.check.repository

import com.shoplworks.hada.svc.entity.CheckTargetChecklistMappingEntity
import com.shoplworks.hada.svc.entity.CheckTargetEntity
import com.shoplworks.hada.svc.entity.ChecklistEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.check.repository
 * fileName : CheckTargetChecklistMappingRepository
 * author : dave
 * date : 2022/04/14
 * description : 최초등록
 */
interface CheckTargetChecklistMappingRepository: JpaRepository<CheckTargetChecklistMappingEntity, Long> {

    @Query("select cm.checklist from tb_check_target_checklist_mapping cm " +
            "where cm.checkTarget = :checkTarget")
    fun findChecklistByCheckTarget(
        @Param("checkTarget") checkTarget: CheckTargetEntity
    ): List<ChecklistEntity>
}