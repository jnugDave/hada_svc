package com.shoplworks.hada.svc.domain.check.service

import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.http.PageableList
import com.shoplworks.hada.svc.domain.check.dto.checklist.*

/**
 * packageName : com.shoplworks.hada.svc.domain.check.service
 * fileName : ChecklistService
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
interface ChecklistService {
    fun createChecklist(requestDto: PostChecklistCreateDto.Request, clientId: Long): Long
    fun deleteChecklists(requestDto: DeleteChecklistDeleteDto.Request, clientId: Long)
    fun updateChecklist(requestDto: PutChecklistUpdateDto.Request, checklistId: Long, clientId: Long)
    fun getChecklist(checklistId: Long): GetChecklistDetailDto.Response
    fun getChecklists(
        page: Int,
        clientId: Long,
        checklistName: String?,
        sortItem: Sort.Checklist?,
        sortDirection: Sort.Direction?): PageableList<GetChecklistPageDto.Response>
}