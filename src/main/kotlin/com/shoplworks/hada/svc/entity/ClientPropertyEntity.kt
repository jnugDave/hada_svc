package com.shoplworks.hada.svc.entity

import com.shoplworks.hada.svc.entity.common.BaseTimeEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * packageName : com.shoplworks.hada.svc.entity
 * fileName : ClientPropertyEntity
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@Entity(name = "tb_client_property")
class ClientPropertyEntity: BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val clientPropertyId: Long? = null

}