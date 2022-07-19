package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.common.enums.UserStatus
import com.shoplworks.hada.svc.common.enums.UserType
import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : ClientEntity
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@Entity(name = "tb_client")
class ClientEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var clientId: Long = 0

    @Column(length = 50, nullable = false)
    lateinit var clientName: String
        protected set

    @Column(length = 200, nullable = false)
    lateinit var clientAddress: String
        protected set

    @Column(length = 200, nullable = false)
    lateinit var clientAddressDetail: String
        protected set

    @Column(length = 10, nullable = false)
    lateinit var zipCode: String
        protected set

    @Column(length = 3, nullable = false)
    lateinit var countryCode: String
        protected set

    @Column(length = 30, nullable = false)
    lateinit var timezone: String
        protected set

    @Column @ColumnDefault("1")
    var isActive: Boolean? = false
        protected set

    @Column @ColumnDefault("0")
    var isDelete: Boolean? = false
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = [CascadeType.ALL], orphanRemoval = true)
    var clientUserList: MutableList<ClientUserEntity> = mutableListOf()

    /** 엔티티 생셩 */
    constructor(clientId: Long): this() {
        this.clientId = clientId
    }

    constructor(clientName: String, clientAddress: String, clientAddressDetail: String, zipCode: String,
                countryCode: String, timeZone: String, owner: MemberEntity): this() {
        this.clientName = clientName
        this.clientAddress = clientAddress
        this.clientAddressDetail = clientAddressDetail
        this.zipCode = zipCode
        this.countryCode = countryCode
        this.timezone = timeZone
        isDelete = false

        //클라이언트 OWNER 권한 유저 생성
        val clientUserEntity = ClientUserEntity(
            this,
            owner
        )

        clientUserList.add(clientUserEntity)
    }

    /** 비즈니스 로직 */
    fun delete() {
        this.isDelete = true
        this.delDt = LocalDateTime.now()
    }
}