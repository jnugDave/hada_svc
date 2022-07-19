package com.shoplworks.hada.svc.domain.client.repository

import com.shoplworks.hada.svc.entity.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * packageName : com.shoplworks.hada.svc.domain.client.repository
 * fileName : ClientRepository
 * author : dave
 * date : 2022/03/29
 * description : 최초등록
 */
interface ClientRepository: JpaRepository<ClientEntity, Long> {
}