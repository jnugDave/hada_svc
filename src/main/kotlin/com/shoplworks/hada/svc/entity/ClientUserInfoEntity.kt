package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import javax.persistence.*

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : ClientUserInfo
 * author : dave
 * date : 2022/04/15
 * description : 최초등록
 */
@Entity(name = "tb_client_user_info")
class ClientUserInfoEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val clientUserInfoId: Long = 0

    @Column(length = 50, nullable = false)
    lateinit var userName: String

    @Column(length = 30, nullable = false)
    lateinit var userPhone: String

    @Column(length = 50)
    var userEmail: String? = null

    @Column(length = 200)
    var memo: String? = null

    constructor(userName: String, userPhone: String, userEmail: String?, memo: String?) : this() {
        this.userName = userName
        this.userPhone = userPhone
        this.userEmail = userEmail
        this.memo = memo
    }
}