package com.shoplworks.hada.svc.domain.member.repository

import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.member.repository
 * fileName : MemberRepository
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
interface MemberRepository: JpaRepository<MemberEntity, Long> {

    fun findByEmail(email: String?): Optional<MemberEntity>

}