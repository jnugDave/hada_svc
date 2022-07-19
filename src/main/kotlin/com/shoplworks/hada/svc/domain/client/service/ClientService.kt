package com.shoplworks.hada.svc.domain.client.service

import com.shoplworks.hada.svc.domain.client.dto.PostClientCreateDto

/**
 * packageName : com.shoplworks.hada.svc.domain.client.service
 * fileName : ClientService
 * author : dave
 * date : 2022/03/28
 * description : 최초등록
 */
interface ClientService {
    fun createClient(requestDto: PostClientCreateDto.Request, memberId: Long): Long
    fun deleteClient(clientId: Long)
}