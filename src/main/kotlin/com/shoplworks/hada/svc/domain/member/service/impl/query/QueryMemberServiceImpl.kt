package com.shoplworks.hada.svc.domain.member.service.impl.query

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.domain.member.dto.GetMemberInfoDto
import com.shoplworks.hada.svc.domain.member.repository.MemberRepository
import com.shoplworks.hada.svc.domain.member.service.QueryMemberService
import org.springframework.stereotype.Service

/**
 * packageName : com.shoplworks.hada.svc.domain.member.service
 * fileName : MemberQueryService
 * author : dave
 * date : 2022/03/28
 * description : 최초등록
 */
@Service
class QueryMemberServiceImpl(
    private val memberRepository: MemberRepository
): QueryMemberService {

    override fun getMemberInfo(memberId: Long): GetMemberInfoDto.Response {

        val memberEntity = memberRepository.findById(memberId).orElseThrow {
            throw CustomException(USER_NOT_FOUND)
        }

        return GetMemberInfoDto.Response().apply {
            this.email = memberEntity.email
            this.nickname = memberEntity.nickname
            this.memberId = memberEntity.memberId
            this.phone = memberEntity.phone
        }
    }
}