package com.shoplworks.hada.svc.domain.client.service.impl

import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.domain.client.dto.PostClientCreateDto
import com.shoplworks.hada.svc.domain.client.repository.ClientRepository
import com.shoplworks.hada.svc.domain.client.repository.ClientUserRepository
import com.shoplworks.hada.svc.domain.client.service.ClientService
import com.shoplworks.hada.svc.domain.member.repository.MemberRepository
import com.shoplworks.hada.svc.entity.ClientEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * packageName : com.shoplworks.hada.svc.domain.client.service
 * fileName : ClientService
 * author : dave
 * date : 2022/03/28
 * description : 최초등록
 */
@Service
@Transactional
class ClientServiceImpl(
    private val clientRepository: ClientRepository,
    private val clientUserRepository: ClientUserRepository,
    private val memberRepository: MemberRepository
) : ClientService {

    /** 클라이언트 생성 */
    override fun createClient(requestDto: PostClientCreateDto.Request, memberId: Long): Long {

        val memberEntity = memberRepository.findById(memberId).orElseThrow {
            throw CustomException(USER_NOT_FOUND)
        }

        //client 생성
        val clientEntity = ClientEntity(
            requestDto.clientName,
            requestDto.clientAddress,
            requestDto.clientAddressDetail,
            requestDto.zipCode,
            requestDto.countryCode,
            requestDto.timezone,
            memberEntity //오너 맴버
        )

        //저장
        clientRepository.save(clientEntity)

        return clientEntity.clientId
    }

    /**
     * 클라이언트 수정
     */

    /** 클라이언트 삭제 */
    override fun deleteClient(clientId: Long) {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }
        //클라이언트 삭제
        clientEntity.delete()
    }

    /**
     * 클라이언트 리스트 조회
     */

    /**
     * 클라이언트 단건 조회
     */

    /**
     * 클라이언트 기능설정(FEATURE) 조회
     */

    /**
     * 클라이언트 기능설정(FEATURE) 수정
     */


}