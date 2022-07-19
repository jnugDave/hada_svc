package com.shoplworks.hada.svc.domain.check.repository

import com.shoplworks.hada.svc.entity.ChecklistEntity
import com.shoplworks.hada.svc.entity.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.check.repository
 * fileName : ChecklistRepository
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
interface ChecklistRepository: JpaRepository<ChecklistEntity, Long> {

    /**
     * 중복된 점검표명이 존재하는지 체크
     */
    @Query("select cl from tb_checklist cl " +
            "where cl.client = :client " +
            "and cl.checklistName = :checklistName " +
            "and cl.isDelete = :isDelete")
    fun findDuplicatedChecklist(
        @Param("client") client: ClientEntity,
        @Param("checklistName") checklistName: String,
        @Param("isDelete") isDelete: Boolean = false): Optional<ChecklistEntity>

    @Query("select cl from tb_checklist cl " +
            "where cl.checklistId in :checklistIds " +
            "and cl.client = :client " +
            "and cl.isDelete = :isDelete")
    fun findAllByClientIdAndChecklistIds(
        @Param("client") client: ClientEntity,
        @Param("checklistIds") checklistIds: List<Long>,
        @Param("isDelete") isDelete: Boolean = false
    ): List<ChecklistEntity>

}