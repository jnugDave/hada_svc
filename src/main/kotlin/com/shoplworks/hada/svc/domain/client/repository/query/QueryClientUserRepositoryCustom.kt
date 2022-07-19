package com.shoplworks.hada.svc.domain.client.repository.query

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.enums.UserType
import com.shoplworks.hada.svc.domain.client.dto.GetClientWorkerListPageDto
import com.shoplworks.hada.svc.entity.ClientEntity
import com.shoplworks.hada.svc.entity.QClientUserEntity.clientUserEntity
import com.shoplworks.hada.svc.entity.QClientUserInfoEntity.clientUserInfoEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/**
 * packageName : com.shoplworks.hada.svc.domain.client.repository.query
 * fileName : QueryClientUserRepositoryCustom
 * author : dave
 * date : 2022/04/16
 * description : 최초등록
 */
@Repository
class QueryClientUserRepositoryCustom(
    private val queryFactory: JPAQueryFactory
) {

    fun getClientWorkerListPage(
        client: ClientEntity,
        sortDirection: Sort.Direction?,
        workerName: String?,
        pageable: Pageable,
        isDelete: Boolean = false
    ): Page<GetClientWorkerListPageDto.Response> {

        val content = queryFactory
            .select(
                Projections.fields(
                    GetClientWorkerListPageDto.Response::class.java,
                    clientUserEntity.clientUserId,
                    clientUserInfoEntity.userName,
                    clientUserInfoEntity.userPhone,
                    clientUserInfoEntity.userEmail,
                    clientUserInfoEntity.memo
                )
            )
            .from(clientUserEntity)
            .innerJoin(clientUserEntity.clientUserInfo, clientUserInfoEntity)
            .where(
                userNameContains(workerName),
                clientUserEntity.client.eq(client),
                clientUserEntity.isDelete.eq(isDelete),
                clientUserEntity.userType.eq(UserType.ROLE_WORKER)
            )
            .orderBy(
                userNameOrder(sortDirection)
            )
            .fetch()

        //드라이븐 테이블에 대한 조건절이 존재하므로 join 생략 불가
        val count = queryFactory
            .select(
                clientUserEntity.clientUserId.count()
            )
            .from(clientUserEntity)
            .innerJoin(clientUserEntity.clientUserInfo, clientUserInfoEntity)
            .where(
                userNameContains(workerName),
                clientUserEntity.client.eq(client),
                clientUserEntity.isDelete.eq(isDelete),
                clientUserEntity.userType.eq(UserType.ROLE_WORKER)
            )
            .fetchOne() ?: 0

        return PageImpl(content, pageable, count)
    }

    /**
     * 작업자명 오름차순 내림차순 정렬
     */
    private fun userNameOrder(sortDirection: Sort.Direction?): OrderSpecifier<String>? {
        return if (sortDirection == Sort.Direction.ASC || sortDirection == null)
            clientUserInfoEntity.userName.asc()
        else
            clientUserInfoEntity.userName.desc()
    }

    /**
     * 점검대상 명으로 검색 여부
     */
    private fun userNameContains(workerName: String?): BooleanExpression? {
        return if (workerName.isNullOrEmpty())
            null
        else
            clientUserInfoEntity.userName.contains(workerName)
    }
}