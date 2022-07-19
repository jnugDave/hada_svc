package com.shoplworks.hada.svc.common.filter.dto

import com.shoplworks.hada.svc.common.enums.UserType
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * packageName : com.shoplworks.hada.svc.domain.member.dto
 * fileName : GetMemberDto
 * author : dave
 * date : 2022/03/26
 * description : 최초등록
 */
class MemberDto {

    class
    Value {

        var id: Long? = null

        var userType: UserType? = null

        var email: String? = null

        var phone: String? = null

        var nickname: String? = null

    }

}