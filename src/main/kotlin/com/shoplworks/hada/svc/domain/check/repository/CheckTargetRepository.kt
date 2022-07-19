package com.shoplworks.hada.svc.domain.check.repository

import com.shoplworks.hada.svc.entity.CheckTargetEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * packageName : com.shoplworks.hada.svc.domain.check.repository
 * fileName : CheckTargetRepository
 * author : dave
 * date : 2022/04/14
 * description : 최초등록
 */
interface CheckTargetRepository: JpaRepository<CheckTargetEntity, Long> {
}