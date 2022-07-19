package com.shoplworks.hada.svc.domain.member.service.impl

import com.shoplworks.hada.svc.common.enums.AuthProvider
import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.utils.JwtTokenProvider
import com.shoplworks.hada.svc.domain.member.dto.SignInDto
import com.shoplworks.hada.svc.domain.member.dto.SignUpDto
import com.shoplworks.hada.svc.domain.member.repository.MemberRepository
import com.shoplworks.hada.svc.domain.member.service.AuthService
import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * packageName : com.shoplworks.hada.svc.domain.member.service
 * fileName : MemberJoinService
 * author : dave
 * date : 2022/03/24
 * description : 최초등록
 */
@Service
class AuthServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) : AuthService {

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    override fun signIn(signInReq: SignInDto.Request): SignInDto.Response {

        try {
            //아이디 패스워드 검증
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    signInReq.email,
                    signInReq.password
                )
            )

            val memberEntity = memberRepository.findByEmail(authentication.name).get()

            //login 성공시 response
            return SignInDto.Response().apply {
                this.token = jwtTokenProvider.generateToken(memberEntity.memberId.toString())
                this.memberId = memberEntity.memberId
            }

        } catch (e: AuthenticationException) {
            throw CustomException(
                ErrorCode.CLIENT_USER_NOT_AUTHORIZED,
                "Invalid credentials supplied"
            )
        }
    }

    /**
     * 이메일로 회원 회원가입
     */
    @Transactional
    override fun signUp(signUpReq: SignUpDto.Request): Long {

        //중복된 이메일을 사용하는 멤버가 있을경우에 예외
        memberRepository.findByEmail(signUpReq.email).ifPresent {
            throw CustomException(ErrorCode.USER_ALREADY_EXIST)
        }

        //비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(signUpReq.password)

        val memberEntity = MemberEntity(signUpReq.email, encodedPassword, signUpReq.nickname, signUpReq.phone, AuthProvider.email)
        val saveMemberEntity = memberRepository.save(memberEntity)

        return saveMemberEntity.memberId!!
    }

}