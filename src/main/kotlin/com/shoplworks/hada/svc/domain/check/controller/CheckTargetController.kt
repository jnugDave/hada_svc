package com.shoplworks.hada.svc.domain.check.controller

import com.shoplworks.hada.svc.common.http.ApiResult
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetDetailDto
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.GetCheckTargetPageDto
import com.shoplworks.hada.svc.domain.check.dto.checkTarget.PostCheckTargetCreateDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.GetChecklistPageDto
import com.shoplworks.hada.svc.domain.check.dto.checklist.PostChecklistCreateDto
import com.shoplworks.hada.svc.domain.check.service.CheckTargetService
import com.shoplworks.hada.svc.domain.client.dto.GetClientWorkerListPageDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
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
 * fileName : CheckTargetController
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
@RestController
@Tag(name = "점검대상 API", description = "점검대상 관련 API")
@PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_SYSTEM_OPERATOR')")
@RequestMapping("api/v1/")
class CheckTargetController(
    private val checkTargetService: CheckTargetService
) {

    @PostMapping("/checkTarget")
    @Operation(summary = "점검대상 생성",
        description = "점검대상 생성 <br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
            ),
            ApiResponse(
                responseCode = "400", description = "유효하지 않은 데이터",
            )
        ]
    )
    fun createCheckTarget(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @AuthenticationPrincipal memberId: Long,
        @Validated @RequestBody requestDto: PostCheckTargetCreateDto.Request
    ): ApiResult<*> {
        val checkTargetId = checkTargetService.createCheckTarget(clientId, requestDto)
        return ApiResult(
            PostCheckTargetCreateDto.Response(checkTargetId)
        )
    }

    @GetMapping("/checkTarget/{checkTargetId}")
    @Operation(summary = "점검대상 상세 조회",
        description = "점검대상 상세 조회<br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [
                    Content(mediaType = "application/json",
                    schema = Schema(implementation = GetCheckTargetDetailDto.Response::class))
                ]
            ),
            ApiResponse(
                responseCode = "404", description = "존재하지 않는 데이터",
            )
        ]
    )
    fun getCheckTarget (
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @Parameter(description = "점검표 ID") @PathVariable("checkTargetId") checkTargetId: Long,
        @AuthenticationPrincipal memberId: Long,
    ): ApiResult<*> {

        return ApiResult(
            checkTargetService.getCheckTarget(checkTargetId)
        )
    }

    @GetMapping("/checkTarget/list")
    @Operation(summary = "점검대상 리스트 페이징",
        description = "점검대상 리스트 페이징 <br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = GetCheckTargetPageDto.Response::class))
                )]

            ),
            ApiResponse(
                responseCode = "404", description = "존재하지 않는 데이터",
            )
        ]
    )
    fun getCheckTargets (
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @AuthenticationPrincipal memberId: Long,
        @Parameter(description = "페이지") @RequestParam(required = false, defaultValue = "0") page: Int
    ): ApiResult<*> {

        return ApiResult(
            checkTargetService.getCheckTargets(page, clientId, null, null, null, null)
        )
    }
}