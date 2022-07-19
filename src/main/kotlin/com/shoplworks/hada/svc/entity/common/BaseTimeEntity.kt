package com.shoplworks.hada.svc.entity.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * packageName : com.shoplworks.hada.svc.entity.common
 * fileName : BaseTimeEntity
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@MappedSuperclass
abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    val regDt: LocalDateTime = LocalDateTime.now()

    @CreatedDate
    @Column(updatable = true)
    val modDt: LocalDateTime = LocalDateTime.now()

    @Column(updatable = true)
    var delDt: LocalDateTime? = null

}