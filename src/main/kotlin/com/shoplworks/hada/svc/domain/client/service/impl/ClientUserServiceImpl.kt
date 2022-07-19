package com.shoplworks.hada.svc.domain.client.service.impl

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.enums.UserType
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.http.PageableList
import com.shoplworks.hada.svc.domain.client.dto.*
import com.shoplworks.hada.svc.domain.client.repository.ClientRepository
import com.shoplworks.hada.svc.domain.client.repository.ClientUserRepository
import com.shoplworks.hada.svc.domain.client.repository.query.QueryClientUserRepositoryCustom
import com.shoplworks.hada.svc.domain.client.service.ClientUserService
import com.shoplworks.hada.svc.domain.member.repository.MemberRepository
import com.shoplworks.hada.svc.entity.ClientUserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.client.service.impl
 * fileName : ClientUserSerivceImpl
 * author : dave
 * date : 2022/04/15
 * description : 최초등록
 */
@Service
@Transactional
class ClientUserServiceImpl(
    private val memberRepository: MemberRepository,
    private val clientUserRepository: ClientUserRepository,
    private val clientRepository: ClientRepository,
    private val queryClientUserRepositoryCustom: QueryClientUserRepositoryCustom
) : ClientUserService {

    /**
     * 클라이언트 작업자 생성
     */
    override fun createClientWorker(
        requestDto: PostClientWorkerCreateDto.Request,
        memberId: Long,
        clientId: Long
    ): Long {

        val regMemberEntity = memberRepository.findById(memberId).orElseThrow {
            throw CustomException(USER_NOT_FOUND)
        }

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        //작업자 저장
        val clientUserEntity = ClientUserEntity(
            clientEntity, regMemberEntity, requestDto.userName,
            requestDto.userPhone, requestDto.userEmail, requestDto.memo
        )

        return clientUserRepository.save(clientUserEntity).clientUserId
    }

    /**
     * 마스터 권한 위임
     */
    override fun updateDelegation(memberId: Long, clientId: Long, clientUserId: Long) {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        val memberEntity = memberRepository.findById(memberId).orElseThrow {
            throw CustomException(USER_NOT_FOUND)
        }
        //오너 클라이언트 유저
        val ownerClientUser =
            clientUserRepository.findClientUserByClientIdAndMemberId(clientEntity, memberEntity).orElseThrow {
                throw CustomException(CLIENT_USER_NOT_FOUND)
            }
        //위임 대상 클라이언트 유저
        val targetClientUser = clientUserRepository.findById(clientUserId).orElseThrow {
            throw CustomException(CLIENT_USER_NOT_FOUND)
        }

        //요청한 클라이언트 유저가 마스터가 아닌경우 예외처리
        if (ownerClientUser.userType != UserType.ROLE_OWNER) {
            throw CustomException(CLIENT_USER_NOT_AUTHORIZED)
        } else {
            //시스템 운영자에게만 권한을 위임할 수 있다.
            if (targetClientUser.userType != UserType.ROLE_SYSTEM_OPERATOR) {
                throw CustomException(BAD_ARGUMENT)
            }
            //마스터 권한으로 변경
            targetClientUser.updateUserType(UserType.ROLE_OWNER)
            //기존 계정은 운영자로 권한 변경
            ownerClientUser.updateUserType(UserType.ROLE_SYSTEM_OPERATOR)
        }
    }

    /**
     * 클라이언트 작업자 업데이트
     */
    override fun updateClientWorker(requestDto: PutClientWorkerUpdateDto.Request, clientUserId: Long) {

        val clientUserEntity = clientUserRepository.findById(clientUserId).orElseThrow {
            throw CustomException(CLIENT_USER_NOT_FOUND)
        }

        clientUserEntity.updateClientWorker(
            requestDto.workerName,
            requestDto.workerPhone,
            requestDto.workerEmail,
            requestDto.memo
        )
    }

    /**
     * 클라이언트 작업자 리스트 페이징 조회
     */
    @Transactional(readOnly = true)
    override fun getClientWorkerList(
        page: Int, clientId: Long, workerName: String?,
        sortDirection: Sort.Direction?
    ): PageableList<GetClientWorkerListPageDto.Response> {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        return PageableList(
            queryClientUserRepositoryCustom.getClientWorkerListPage(
                clientEntity,
                sortDirection,
                workerName,
                PageRequest.of(page, 10)
            )
        )
    }

    /**
     * 클라이이언트 작업자 단건 조회
     */
    override fun getClientWorker(clientUserId: Long): GetClientWorkerDto.Response {
        val clientUserEntity = clientUserRepository.findClientWorkerByClientUserId(clientUserId).orElseThrow {
            throw CustomException(CLIENT_USER_NOT_FOUND)
        }

        return GetClientWorkerDto.Response().of(clientUserEntity)
    }
}