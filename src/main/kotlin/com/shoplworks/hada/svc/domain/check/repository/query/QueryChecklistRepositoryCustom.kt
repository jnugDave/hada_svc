package com.shoplworks.hada.svc.domain.check.repository.query

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.domain.check.dto.checklist.GetChecklistPageDto
import com.shoplworks.hada.svc.entity.*
import com.shoplworks.hada.svc.entity.QCheckFrequencyEntity.checkFrequencyEntity
import com.shoplworks.hada.svc.entity.QChecklistEntity.checklistEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/**
 * packageName : com.shoplworks.hada.svc.domain.check.repository.query
 * fileName : QueryChecklistRepository
 * author : dave
 * date : 2022/04/12
 * description : 최초등록
 */
@Repository
class QueryChecklistRepositoryCustom(
    private val queryFactory: JPAQueryFactory
) {

    fun getChecklistPage(
        client: ClientEntity,
        sortItem: Sort.Checklist?,
        sortDirection: Sort.Direction?,
        pageable: Pageable
    ): Page<GetChecklistPageDto.Value> {

        //Fetch join의 사용목적은 엔티티 상태에서 엔티티 그래프를 참조하기 위해서 사용하는 것이기 때문에
        //fetch join 에서 dto 조회는 할 수 없다. 따라서 일반 이너 조인 사용
        val content = queryFactory
            .select(
                Projections.fields(
                    GetChecklistPageDto.Value::class.java,
                    checklistEntity.checklistId,
                    checklistEntity.checklistName,
                    checklistEntity.checkFrequency,
                    checklistEntity.checkTargetCount
                )
            )
            .from(checklistEntity)
            .innerJoin(checklistEntity.checkFrequency, checkFrequencyEntity)
            .where(
                checklistEntity.client.eq(client)
            )
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .orderBy(
                checkTargetOrder(sortItem, sortDirection)
            )
            .fetch()

        //조인 없어도 count 결과에는 아무런 영향을 끼치지 않으므로
        //성능 최적화를 위해 조인 없앴다.
        val count = queryFactory
            .select(
                checklistEntity.checklistId.count()
            )
            .from(checklistEntity)
            .where(
                checklistEntity.client.eq(client)
            )
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetchOne() ?: 0

        return PageImpl(content, pageable, count)
    }

    /**
     * 점검대상 검색 정렬 조건 체크
     */
    private fun checkTargetOrder(sortItem: Sort.Checklist?, sortDirection: Sort.Direction?): OrderSpecifier<*> {
        return if (sortItem == Sort.Checklist.CHECKLIST_NAME || sortItem == null){
            if(sortDirection == Sort.Direction.ASC || sortDirection == null) {
                checklistEntity.checklistName.asc()
            }
            else {
                checklistEntity.checklistName.desc()
            }
        }
        else {
            if(sortDirection == Sort.Direction.ASC || sortDirection == null) {
                checklistEntity.checkTargetCount.asc()
            }
            else {
                checklistEntity.checkTargetCount.desc()
            }
        }
    }
}