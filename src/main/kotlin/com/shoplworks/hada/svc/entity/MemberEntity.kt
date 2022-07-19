package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.common.enums.AuthProvider
import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import org.hibernate.annotations.ColumnDefault
import javax.persistence.*

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : MemberEntity
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@Entity(name = "tb_member")
class MemberEntity protected constructor(): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null

    @Column(length = 50, nullable = false)
    lateinit var email: String
        protected set

    @Column(length = 255, nullable = false)
    lateinit var password: String
        protected set

    @Column(length = 20, nullable = false)
    lateinit var nickname: String
        protected set

    @Column(length = 20)
    var phone: String? = null
        protected set

    @Column(length = 255)
    var imageUrl: String? = null
        protected set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var provider: AuthProvider? = null
        protected set

    @Column @ColumnDefault("0")
    var isDelete: Boolean? = false
        protected set

    /** 엔티티 생셩 */
    constructor(email: String, password: String, nickname: String, phone: String?, provider: AuthProvider, imageUrl: String? = null): this() {
        this.email = email
        this.password = password
        this.nickname = nickname
        this.phone = phone
        this.isDelete = false
        this.provider = provider
        this.imageUrl = imageUrl
    }

}