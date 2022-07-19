package com.shoplworks.hada.svc.domain.member.service

import com.shoplworks.hada.svc.domain.member.dto.SignUpDto

/**
 * packageName : com.shoplworks.hada.svc.domain.member.service
 * fileName : MemberService
 * author : dave
 * date : 2022/03/24
 * description : 최초등록
 */
interface MemberService {
    fun joinMember(requestDto: SignUpDto.Request): Long
}