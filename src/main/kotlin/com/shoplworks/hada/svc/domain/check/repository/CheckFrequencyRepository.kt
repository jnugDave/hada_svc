package com.shoplworks.hada.svc.domain.check.repository

import com.shoplworks.hada.svc.entity.CheckFrequencyEntity
import com.shoplworks.hada.svc.entity.ChecklistItemEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * packageName : com.shoplworks.hada.svc.domain.check.repository
 * fileName : CheckFrequencyRepository
 * author : dave
 * date : 2022/04/07
 * description : 최초등록
 */
interface CheckFrequencyRepository: JpaRepository<CheckFrequencyEntity, Long> {
}