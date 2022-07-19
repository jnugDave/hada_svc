package com.shoplworks.hada.svc.domain.check.controller

import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.http.ApiResult
import com.shoplworks.hada.svc.domain.check.dto.checklist.*
import com.shoplworks.hada.svc.domain.check.service.ChecklistService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * packageName : com.shoplworks.hada.svc.domain.client.controller
 * fileName : ChecklistController
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@RestController
@Tag(name = "점검표 API", description = "점검표 관련 API")
@PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_SYSTEM_OPERATOR')")
@RequestMapping("api/v1/")
class ChecklistController(
    private val checklistService: ChecklistService
) {

    @PostMapping("/checklist")
    @Operation(summary = "점검표 생성",
        description = "점검표 생성 <br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
            ),
            ApiResponse(
                responseCode = "400", description = "유효하지 않은 데이터",
            ),
            ApiResponse(
                responseCode = "409002", description = "점검표명 중복",
            ),
        ]
    )
    fun createChecklist(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @AuthenticationPrincipal memberId: Long,
        @Validated @RequestBody requestDto: PostChecklistCreateDto.Request
    ): ApiResult<*> {
        val checklistId = checklistService.createChecklist(requestDto, clientId)
        return ApiResult(
            PostChecklistCreateDto.Response(checklistId)
        )
    }

    @DeleteMapping(value = ["/checklist"])
    @Operation(summary = "점검표 삭제",
        description = "점검표 삭제<br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공"
            ),
            ApiResponse(
                responseCode = "404", description = "존재하지 않는 리소스",
            ),
        ]
    )
    fun deleteCheckTarget(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @RequestBody requestDto: DeleteChecklistDeleteDto.Request
    ): ApiResult<*> {
        checklistService.deleteChecklists(requestDto, clientId)
        return ApiResult(null)
    }

    @PutMapping(value = ["checklist/{checklistId}"])
    @Operation(summary = "점검표 업데이트",
        description = "점검표 업데이트<br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공"
            )
        ]
    )
    fun updateCheckTarget(
        @Parameter(description = "점검표 ID") @PathVariable("checklistId") checklistId: Long,
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @Validated @RequestBody requestDto: PutChecklistUpdateDto.Request
    ): ApiResult<*> {
        checklistService.updateChecklist(requestDto, checklistId, clientId)
        return ApiResult(null)
    }

    @GetMapping(value = ["checklist/{checklistId}"])
    @Operation(summary = "점검표 조회",
        description = "점검표 조회<br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = GetChecklistDetailDto.Response::class))]
            )
        ]
    )
    fun getChecklist(
        @Parameter(description = "점검표 ID") @PathVariable("checklistId") checklistId: Long,
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long
    ): ApiResult<*> {
        val getChecklistDetailDto = checklistService.getChecklist(checklistId)
        return ApiResult(getChecklistDetailDto)
    }

    @GetMapping(value = ["checklist/list"])
    @Operation(summary = "점검표 리스트",
        description = "점검표 리스트<br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = GetChecklistPageDto.Response::class))]
            )
        ]
    )
    fun getChecklists(
        @Parameter(description = "클라이언트 ID") @RequestParam  clientId: Long,
        @Parameter(description = "검색(점검표 명)") @RequestParam(required = false) checklistName: String?,
        @Parameter(description = "정렬") @RequestParam(required = false, defaultValue = "CHECKLIST_NAME") sortItem: Sort.Checklist?,
        @Parameter(description = "정렬방향") @RequestParam(required = false, defaultValue = "ASC") sortDirection: Sort.Direction?,
        @Parameter(description = "페이지") @RequestParam(required = false, defaultValue = "0") page: Int
    ): ApiResult<*> {

        return ApiResult(
            checklistService.getChecklists(page, clientId, checklistName, sortItem, sortDirection)
        )
    }
}