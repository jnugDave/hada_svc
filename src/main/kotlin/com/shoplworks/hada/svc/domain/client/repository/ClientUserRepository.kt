package com.shoplworks.hada.svc.domain.client.repository

import com.shoplworks.hada.svc.common.enums.UserType
import com.shoplworks.hada.svc.entity.ClientEntity
import com.shoplworks.hada.svc.entity.ClientUserEntity
import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.client.repository
 * fileName : ClientUserRepository
 * author : dave
 * date : 2022/03/29
 * description : 최초등록
 */
interface ClientUserRepository: JpaRepository<ClientUserEntity, Long> {

    /**
     * 인증 필터에서 해당 맴버가 클라이언트에서 어떤 권한을 가지고 있는지 체크하기 위해 조회
     */
    @Query("select cu from tb_client_user cu join fetch cu.member m " +
            "where cu.client = :client " +
            "and m.memberId = :memberId " +
            "and cu.isDelete = :isDelete")
    fun checkMemberValid(
        @Param("client") client: ClientEntity,
        @Param("memberId") memberId: Long,
        @Param("isDelete") isDelete: Boolean = false
    ): Optional<ClientUserEntity>

    /**
     * 클라이언트 id 와 맴버 id 로 클라이언트 유저 엔티티 조회
     */
    @Query("select cu from tb_client_user cu " +
            "where cu.client = :client " +
            "and cu.member = :member " +
            "and cu.isDelete = :isDelete")
    fun findClientUserByClientIdAndMemberId(
        @Param("client") client: ClientEntity,
        @Param("member") member: MemberEntity,
        @Param("isDelete") isDelete: Boolean = false
    ): Optional<ClientUserEntity>

    /**
     * 클라이언트 유저아이디를 가지고 작업자 정보 조회
     */
    @Query("select cu from tb_client_user cu " +
            "join fetch cu.clientUserInfo ci " +
            "where cu.clientUserId = :clientUserId " +
            "and cu.isDelete = :isDelete " +
            "and cu.userType = :userType")
    fun findClientWorkerByClientUserId(
        @Param("clientUserId") clientUserId: Long,
        @Param("userType") userType: UserType = UserType.ROLE_WORKER,
        @Param("isDelete") isDelete: Boolean = false
    ): Optional<ClientUserEntity>
}