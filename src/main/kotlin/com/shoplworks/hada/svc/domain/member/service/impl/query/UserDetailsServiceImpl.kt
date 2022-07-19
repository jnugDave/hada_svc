package com.shoplworks.hada.svc.domain.member.service.impl.query

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.filter.dto.MemberDto
import com.shoplworks.hada.svc.domain.client.repository.ClientUserRepository
import com.shoplworks.hada.svc.domain.member.repository.MemberRepository
import com.shoplworks.hada.svc.entity.ClientEntity
import com.shoplworks.hada.svc.entity.ClientUserEntity
import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * packageName : com.shoplworks.hada.svc.domain.member.service.impl
 * fileName : MemberDetailsService
 * author : dave
 * date : 2022/03/26
 * description : 최초등록
 */
@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository,
    private val clientUserRepository: ClientUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails? {
        //일치하는 유저 이메일이 없을 경우
        val memberEntity = memberRepository.findByEmail(email).orElseThrow {
            throw CustomException(ErrorCode.USER_NOT_FOUND, "user not found")
        }

        //유저 권한 리스트
        val authority: List<GrantedAuthority> = listOf()

        return User(memberEntity.email, memberEntity.password, authority)
    }

    /**
     * 이메일을 이용해 멤버 정보 조회
     */
    fun getUserDetailsByEmail(email: String): MemberDto.Value {
        val memberEntity = memberRepository.findByEmail(email).orElseThrow {
            throw CustomException(ErrorCode.USER_NOT_FOUND, "user not found")
        }

        return MemberDto.Value().apply {
            this.id = memberEntity.memberId
            this.email = memberEntity.email
            this.nickname = memberEntity.nickname
            this.phone = memberEntity.phone
        }
    }

    /**
     * id를 이용해 클라이언트 유저 조회
     * 클라이언트 유저가 존재해야 유요한 사용자로 인정된다
     */
    fun getUserDetailsById(memberId: Long, clientId: Long?): MemberDto.Value {

        var clientUser: ClientUserEntity? = null

        clientId?.let {
            clientUser = clientUserRepository.checkMemberValid(ClientEntity(clientId), memberId).orElseThrow {
                throw CustomException(ErrorCode.USER_NOT_FOUND, "user not found")
            }
        }

        return MemberDto.Value().apply {
            this.id = memberId
            this.userType = clientUser?.userType
        }
    }
}