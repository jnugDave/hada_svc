package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.enums.ErrorCode.*
import com.shoplworks.hada.svc.common.enums.UserStatus
import com.shoplworks.hada.svc.common.enums.UserType
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import org.hibernate.annotations.ColumnDefault
import javax.persistence.*

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : ClientUserEntity
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@Entity(name = "tb_client_user")
class ClientUserEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val clientUserId: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    lateinit var client: ClientEntity
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    lateinit var member: MemberEntity
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_member_id")
    lateinit var regMember: MemberEntity
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var userType: UserType
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    lateinit var userStatus: UserStatus
        protected set

    @Column @ColumnDefault("0")
    var isDelete: Boolean = false
        protected set

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "client_user_info_id")
    var clientUserInfo: ClientUserInfoEntity? = null

    /**
     * 마스터 생성
     */
    constructor(client: ClientEntity, member: MemberEntity) : this() {
        this.client = client
        this.member = member
        this.userStatus = UserStatus.ACTIVE //생성시 활성화
        this.userType = UserType.ROLE_OWNER
//        //작업자 정보 생성
//        this.clientUserInfo = ClientUserInfoEntity(userName, userPhone, userEmail, memo)
    }

    /**
     * 작업자 생성
     */
    constructor(client: ClientEntity, regMember: MemberEntity, userName: String,
                userPhone: String, userEmail: String?, memo: String?) : this() {
        this.client = client
        this.regMember = regMember
        this.userStatus = UserStatus.ACTIVE //생성시 활성화
        this.isDelete = false
        this.userType = UserType.ROLE_WORKER

        //작업자 정보 생성
        this.clientUserInfo = ClientUserInfoEntity(userName, userPhone, userEmail, memo)
    }

    /**
     * 비즈니스 로직
     */
    fun updateClientWorker(userName: String, userPhone: String, userEmail: String?, memo: String?) {
        //작업자가 아닌 유저를 변경하려고 하면 예외
        if(this.userType!=UserType.ROLE_WORKER) {
            throw CustomException(BAD_ARGUMENT)
        }
        //삭제된 유저 예외처리
        if(this.isDelete) {
            throw CustomException(CLIENT_USER_NOT_FOUND)
        }
        //유저 정보 업데이트
        this.clientUserInfo.apply {
            this?.userName = userName
            this?.userPhone = userPhone
            this?.userEmail = userEmail
            this?.memo = memo
        }
    }

    fun updateUserType(userType: UserType) {
        this.userType = userType
    }
}