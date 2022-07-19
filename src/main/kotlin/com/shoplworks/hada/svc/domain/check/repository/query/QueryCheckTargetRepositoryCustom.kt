package com.shoplworks.hada.svc.domain.check.repository.query

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetPageDto
import com.shoplworks.hada.svc.entity.ClientEntity
import com.shoplworks.hada.svc.entity.QCheckTargetChecklistMappingEntity.checkTargetChecklistMappingEntity
import com.shoplworks.hada.svc.entity.QCheckTargetEntity.checkTargetEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/**
 * packageName : com.shoplworks.hada.svc.domain.check.repository.query
 * fileName : QueryCheckTargetRespository
 * author : dave
 * date : 2022/04/14
 * description : 최초등록
 */
@Repository
class QueryCheckTargetRepositoryCustom(
    private val queryFactory: JPAQueryFactory
) {

    fun getCheckTargetPage(
        client: ClientEntity,
        pageable: Pageable,
        checkTargetIds: List<Long>?,
        checkTargetName: String?,
        sortItem: Sort.CheckTarget?,
        sortDirection: Sort.Direction?,
        isDelete: Boolean = false
    ): Page<GetCheckTargetPageDto.Row> {

        //페이징 내용
        //dto 조회
        val content = queryFactory
            .select(
                Projections.fields(
                    GetCheckTargetPageDto.Row::class.java,
                    checkTargetEntity.checkTargetId,
                    checkTargetEntity.checkTargetName,
                    checkTargetEntity.lastCheckDt
                )
            )
            .from(checkTargetEntity)
            .where(
                checkTargetIdsIn(checkTargetIds),
                checkTargetNameContains(checkTargetName),
                checkTargetEntity.client.eq(client),
                checkTargetEntity.isDelete.eq(isDelete)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(
                checkTargetOrder(sortItem, sortDirection)
            )
            .fetch()

        //페이징 아이템 총 갯수
        val count = queryFactory
            .select(checkTargetEntity.checkTargetId.count())
            .from(checkTargetEntity)
            .where(
                checkTargetIdsIn(checkTargetIds),
                checkTargetNameContains(checkTargetName),
                checkTargetEntity.client.eq(client),
                checkTargetEntity.isDelete.eq(isDelete)
            )
            .fetchOne() ?: 0

        return PageImpl(content, pageable, count)

    }

    /**
     * 점검 대상명으로 검색하면 동적쿼리 추가
     */
    private fun checkTargetNameContains(checkTargetName: String?): BooleanExpression? {
        return if (checkTargetName.isNullOrEmpty())
            null
        else
            checkTargetEntity.checkTargetName.contains(checkTargetName)
    }

    /**
     * 점검 리스트로 검색하면 동적쿼리 추가
     */
    private fun checkTargetIdsIn(checkTargetIds: List<Long>?): BooleanExpression? {
        return if (checkTargetIds == null)
            null
        else
            checkTargetEntity.checkTargetId.`in`(checkTargetIds)
    }


    /**
     * 점검대상 검색 정렬 조건 체크
     */
    private fun checkTargetOrder(sortItem: Sort.CheckTarget?, sortDirection: Sort.Direction?): OrderSpecifier<*> {
        return if (sortItem == Sort.CheckTarget.CHECK_TARGET_NAME || sortItem == null){
            if(sortDirection == Sort.Direction.ASC || sortDirection == null) {
                checkTargetEntity.checkTargetName.asc()
            }
            else {
                checkTargetEntity.checkTargetName.desc()
            }
        }
        else {
            if(sortDirection == Sort.Direction.ASC || sortDirection == null) {
                checkTargetEntity.lastCheckDt.asc()
            }
            else {
                checkTargetEntity.lastCheckDt.desc()
            }
        }
    }

    /**
     * 점검대상 pk 리스트를 제공하면 점검대상에 속한 점검표 데이터를 dto 형태로 반환한다.
     */
    fun findChecklistMap (checkTargetIds: List<Long>?): Map<Long, List<GetCheckTargetPageDto.ChecklistDto>> {
        return queryFactory
            .select(
                Projections.fields(
                    GetCheckTargetPageDto.ChecklistDto::class.java,
                    checkTargetChecklistMappingEntity.checkTarget.checkTargetId,
                    checkTargetChecklistMappingEntity.checklist.checklistId,
                    checkTargetChecklistMappingEntity.checklist.checklistName
                )
            )
            .from(
                checkTargetChecklistMappingEntity
            )
            .join(checkTargetChecklistMappingEntity.checklist)
            .join(checkTargetChecklistMappingEntity.checkTarget)
            .where(
                checkTargetChecklistMappingEntity.checkTarget.checkTargetId.`in`(checkTargetIds)
            )
            .fetch()
                //list -> map
            .groupBy {
                it.checkTargetId
            }
    }

    /**
     * 점검표 아이디 리스트를 이용해서 점검표가 적용된 모든 점검대상 아이디를 가져온다
     */
    fun findCheckTargetIds(checklistIds: List<Long>): List<Long> {
        return queryFactory
            .select(checkTargetChecklistMappingEntity.checkTarget.checkTargetId)
            .distinct()
            .join(checkTargetChecklistMappingEntity.checklist)
            .where(checkTargetChecklistMappingEntity.checklist.checklistId.`in`(checklistIds))
            .fetch()
    }
}