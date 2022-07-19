package com.shoplworks.hada.svc.domain.member.service

import com.shoplworks.hada.svc.domain.member.dto.GetMemberInfoDto

/**
 * packageName : com.shoplworks.hada.svc.domain.member.service
 * fileName : MemberQueryService
 * author : dave
 * date : 2022/03/28
 * description : 최초등록
 */
interface QueryMemberService {
    fun getMemberInfo(memberId: Long): GetMemberInfoDto.Response
}