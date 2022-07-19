package com.shoplworks.hada.svc.domain.client.service

import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.http.PageableList
import com.shoplworks.hada.svc.domain.client.dto.GetClientWorkerDto
import com.shoplworks.hada.svc.domain.client.dto.GetClientWorkerListPageDto
import com.shoplworks.hada.svc.domain.client.dto.PostClientWorkerCreateDto
import com.shoplworks.hada.svc.domain.client.dto.PutClientWorkerUpdateDto
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.domain.client.service
 * fileName : ClientUserService
 * author : dave
 * date : 2022/04/15
 * description : 최초등록
 */
interface ClientUserService {
    fun createClientWorker(requestDto: PostClientWorkerCreateDto.Request, memberId: Long, clientId: Long): Long
    fun updateDelegation(memberId: Long, clientId: Long, clientUserId: Long)
    fun updateClientWorker(requestDto: PutClientWorkerUpdateDto.Request, clientUserId: Long)
    fun getClientWorkerList(
        page: Int,
        clientId: Long,
        userName: String?,
        sortDirection: Sort.Direction?
    ): PageableList<GetClientWorkerListPageDto.Response>

    fun getClientWorker(
        clientUserId: Long
    ): GetClientWorkerDto.Response
}