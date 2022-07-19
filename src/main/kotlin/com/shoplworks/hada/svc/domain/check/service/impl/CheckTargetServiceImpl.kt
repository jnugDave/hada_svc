package com.shoplworks.hada.svc.domain.check.service.impl

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.http.PageableList
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetDetailDto
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetPageDto
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.PostCheckTargetCreateDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.GetChecklistDetailDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.GetChecklistPageDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.PostChecklistCreateDto
import com.shoplworks.hada.svc.domain.check.repository.CheckTargetChecklistMappingRepository
import com.shoplworks.hada.svc.domain.check.repository.CheckTargetRepository
import com.shoplworks.hada.svc.domain.check.repository.ChecklistRepository
import com.shoplworks.hada.svc.domain.check.repository.query.QueryCheckTargetRepositoryCustom
import com.shoplworks.hada.svc.domain.check.service.CheckTargetService
import com.shoplworks.hada.svc.domain.client.repository.ClientRepository
import com.shoplworks.hada.svc.entity.CheckTargetEntity
import com.shoplworks.hada.svc.entity.ChecklistEntity
import com.shoplworks.hada.svc.vo.CheckTargetAttrVo
import org.modelmapper.ModelMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

/**
 * packageName : com.shoplworks.hada.svc.domain.check.service.impl
 * fileName : CheckTargetSerivceImpl
 * author : dave
 * date : 2022/04/13
 * description : 최초등록
 */
@Service
@Transactional
class CheckTargetServiceImpl(
    private val clientRepository: ClientRepository,
    private val checklistRepository: ChecklistRepository,
    private val checkTargetRepository: CheckTargetRepository,
    private val checkTargetChecklistMappingRepository: CheckTargetChecklistMappingRepository,
    private val queryCheckTargetRepositoryCustom: QueryCheckTargetRepositoryCustom,
): CheckTargetService {

    /**
     * 점검대상 등록
     */
    override fun createCheckTarget(clientId: Long, requestDto: PostCheckTargetCreateDto.Request): Long {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }
        //점검대상에 할당된 점검표 리스트
        val checklistList = getChecklistsFromDto(requestDto.checklistMappingList)

        //점검대상 생성
        val checkTargetEntity = CheckTargetEntity(
            clientEntity,
            requestDto.checkTargetName,
            requestDto.checkTargetAttr,
            checklistList
        )
        //저장
        val saveCheckTargetEntity = checkTargetRepository.save(checkTargetEntity)

        return saveCheckTargetEntity.checkTargetId
    }

    /**
     * 점검표리스트 dto -> entity 변경
     */
    private fun getChecklistsFromDto(checklistMappingList: List<PostCheckTargetCreateDto.ChecklistMappingDto>):
            List<ChecklistEntity> {
        val checklistList = arrayListOf<ChecklistEntity>()
        //아이템 순서 정렬
        checklistMappingList.sortedBy { i -> i.itemOrder }.forEach { checklistMapping ->
            //점검표 엔티티 조회
            val checklist = checklistRepository.findById(checklistMapping.checklistId).orElseThrow {
                throw CustomException(RESOURCE_NOT_FOUND)
            }

            checklistList.add(checklist)
        }

        return checklistList
    }

    /**
     * 점검대상 리스트 삭제
     */
    override fun deleteCheckTargets() {

    }

    /**
     * 점검대상 업데이트
     */
    override fun updateCheckTarget() {

    }

    /**
     * 점검대상 상세 조회
     */
    override fun getCheckTarget(checkTargetId: Long): GetCheckTargetDetailDto.Response {

        val checkTargetEntity = checkTargetRepository.findById(checkTargetId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        val checklists =
            checkTargetChecklistMappingRepository.findChecklistByCheckTarget(checkTargetEntity)

        return GetCheckTargetDetailDto.Response().of(checkTargetEntity, checklists)
    }

    /**
     * 점검대상 리스트 페이징 조회
     */
    @Transactional(readOnly = true)
    override fun getCheckTargets(
        page: Int,
        clientId: Long,
        checklistIds: List<Long>?,
        checkTargetName: String?,
        sortItem: Sort.CheckTarget?,
        sortDirection: Sort.Direction?
    ): PageableList<GetCheckTargetPageDto.Response> {

        val clientEntity = clientRepository.findById(clientId).orElseThrow {
            throw CustomException(RESOURCE_NOT_FOUND)
        }

        //점검표 아이디 리스트를 이용해서 해당 점검표가 적용된 모든 점검장소 pk를 가져온다
        val checkTargetIdsToSearch =
            if (checklistIds.isNullOrEmpty())
                null
            else queryCheckTargetRepositoryCustom.findCheckTargetIds(checklistIds)

        //페이징 검색
        val checkTargetPage = queryCheckTargetRepositoryCustom.getCheckTargetPage(
            clientEntity,
            PageRequest.of(page, 10),
            checkTargetIdsToSearch,
            checkTargetName,
            sortItem,
            sortDirection,
        )

        //조회된 점검대상의 id 리스트를 구한다
        val checkTargetIdsResult = toIds(checkTargetPage.toList())

        //점검표 리스트는 oneToMany 연관관계 이므로 따로 조회
        val checklistMap = queryCheckTargetRepositoryCustom.findChecklistMap(checkTargetIdsResult)

        return PageableList(
            checkTargetPage.map { row ->
                //점검표 리스트를 맵에서 가져온다
                val checklists = checklistMap[row.checkTargetId] ?: listOf()
                GetCheckTargetPageDto.Response().of(row, checklists)
            }
        )
    }

    /**
     * 아이디만 가지고 있는 리스트로 반환
     * in 쿼리에 전달하기 위해
     */
    private fun toIds(list: List<GetCheckTargetPageDto.Row>): List<Long> {
        return list.map { it.checkTargetId }.toList()
    }
}