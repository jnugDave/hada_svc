package com.shoplworks.hada.svc.common.filter.dto


/**
 * packageName : com.shoplworks.hada.svc.domain.member.dto
 * fileName : GetMemberDto
 * author : dave
 * date : 2022/03/26
 * description : 최초등록
 */
class LoginDto {

    class Response {
        var memberId: Long? = null
        var token: String? = null
    }
}