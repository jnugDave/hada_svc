package com.shoplworks.hada.svc.domain.member.service

import com.shoplworks.hada.svc.common.filter.dto.LoginDto
import com.shoplworks.hada.svc.domain.member.dto.SignInDto
import com.shoplworks.hada.svc.domain.member.dto.SignUpDto

/**
 * packageName : com.shoplworks.hada.svc.domain.member.service
 * fileName : AuthService
 * author : dave
 * date : 2022/07/16
 * description : 최초등록
 */
interface AuthService {

    fun signIn(signInReq: SignInDto.Request): SignInDto.Response
    fun signUp(signUpReq: SignUpDto.Request): Long
}