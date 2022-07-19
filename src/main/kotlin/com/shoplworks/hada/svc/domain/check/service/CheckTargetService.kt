package com.shoplworks.hada.svc.domain.check.service

import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.http.PageableList
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetDetailDto
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetPageDto
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.PostCheckTargetCreateDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.GetChecklistDetailDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.GetChecklistPageDto

/**
 * packageName : com.shoplworks.hada.svc.domain.check.service
 * fileName : CheckTargetService
 * author : dave
 * date : 2022/04/13
 * description : 최초등록
 */
interface CheckTargetService {
    fun createCheckTarget(clientId: Long, requestDto: PostCheckTargetCreateDto.Request): Long
    fun deleteCheckTargets()
    fun updateCheckTarget()
    fun getCheckTarget(checkTargetId: Long): GetCheckTargetDetailDto.Response
    fun getCheckTargets(
        page: Int,
        clientId: Long,
        checklistIds: List<Long>?,
        checkTargetName: String?,
        sortItem: Sort.CheckTarget?,
        sortDirection: Sort.Direction?
    ): PageableList<GetCheckTargetPageDto.Response>
}