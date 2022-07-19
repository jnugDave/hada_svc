package com.shoplworks.hada.svc.domain.check.service.impl

import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.http.PageableList
import com.shoplworks.hada.svc.domain.check.dto.checklist.*
import com.shoplworks.hada.svc.domain.check.repository.ChecklistRepository
import com.shoplworks.hada.svc.domain.check.service.ChecklistService
import com.shoplworks.hada.svc.domain.check.repository.query.QueryChecklistRepositoryCustom
import com.shoplworks.hada.svc.domain.client.repository.ClientRepository
import com.shoplworks.hada.svc.entity.ChecklistEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * packageName : com.shoplworks.hada.svc.domain.check.service.impl
 * fileName : ChecklistServiceImpl
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@Service
class ChecklistServiceImpl(
    private val checklistRepository: ChecklistRepository,
    private val clientRepository: ClientRepository,
    private val queryChecklistRepositoryCustom: QueryChecklistRepositoryCustom
): ChecklistService {

    /**
     * 점검표 생성
     */
    @Transactional
    override fun createChecklist(requestDto: PostChecklistCreateDto.Request, clientId: Long): Long {

        //일치하는 클라이언트가 없음
        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        //일치하는 점겸표 명이 존재
        checklistRepository.findDuplicatedChecklist(clientEntity, requestDto.checklistName).ifPresent {
            throw CustomException(RESOURCE_ALREADY_EXIST)
        }

        //점검표 엔티티 생성
        val checklistEntity = ChecklistEntity(clientEntity, requestDto.checklistName, requestDto.checklistAttr,
                requestDto.checkFrequency.checkCycle, requestDto.checkFrequency.checkCycleAttr, requestDto.checklistItems)

        //점검표 저장 (점검빈도, 점검항목 함께 저장된다)
        val saveChecklistEntity = checklistRepository.save(checklistEntity)

        return saveChecklistEntity.checklistId
    }

    /**
     * 점검표 리스트 삭제
     */
    @Transactional
    override fun deleteChecklists(requestDto: DeleteChecklistDeleteDto.Request, clientId: Long) {

        //일치하는 클라이언트가 없음
        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        val checklists = checklistRepository.findAllByClientIdAndChecklistIds(clientEntity, requestDto.checklistIds)

        //삭제
        checklists.forEach { checklist ->
            checklist.delete()
        }
    }

    /**
     * 점검표 업데이트
     */
    @Transactional
    override fun updateChecklist(requestDto: PutChecklistUpdateDto.Request, checklistId: Long, clientId: Long) {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        //점검표명 중복 체크
        checklistRepository.findDuplicatedChecklist(clientEntity, requestDto.checklistName)
            .ifPresent { checklist ->
                if(checklist.checklistId != checklistId)
                 throw CustomException(RESOURCE_NAME_ALREADY_EXIST)
            }

        val checklistEntity = checklistRepository.findById(checklistId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        //점검표 업데이트
        checklistEntity.update(
            requestDto.checklistAttr,
            requestDto.checklistName,
            requestDto.checkFrequency.checkCycle,
            requestDto.checkFrequency.checkCycleAttr,
            requestDto.checklistItems
        )
    }

    /**
     * 점검표 상세정보 조회
     */
    @Transactional(readOnly = true)
    override fun getChecklist(checklistId: Long): GetChecklistDetailDto.Response {
        //점검표 조회
        val checklistEntity = checklistRepository.findById(checklistId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }


        return GetChecklistDetailDto.Response().of(checklistEntity)
    }

    /**
     * 점검표 리스트 페이징
     */
    @Transactional(readOnly = true)
    override fun getChecklists(
        page: Int,
        clientId: Long,
        checklistName: String?,
        sortItem: Sort.Checklist?,
        sortDirection: Sort.Direction?
    ): PageableList<GetChecklistPageDto.Response> {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        return PageableList(
            queryChecklistRepositoryCustom.getChecklistPage(
                clientEntity,
                sortItem,
                sortDirection,
                PageRequest.of(page, 10)
            ).map { value ->
                GetChecklistPageDto.Response().of(value)
            }
        )
    }
}