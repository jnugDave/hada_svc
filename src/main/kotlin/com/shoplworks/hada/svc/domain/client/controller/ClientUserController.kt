package com.shoplworks.hada.svc.domain.client.controller

import com.shoplworks.hada.svc.common.enums.Sort
import com.shoplworks.hada.svc.common.http.ApiResult
import com.shoplworks.hada.svc.domain.client.dto.GetClientWorkerDto
import com.shoplworks.hada.svc.domain.client.dto.GetClientWorkerListPageDto
import com.shoplworks.hada.svc.domain.client.dto.PostClientWorkerCreateDto
import com.shoplworks.hada.svc.domain.client.dto.PutClientWorkerUpdateDto
import com.shoplworks.hada.svc.domain.client.service.ClientUserService
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
 * fileName : ClientUserController
 * author : dave
 * date : 2022/04/15
 * description : 최초등록
 */
@Tag(name = "클라이언트 사용자 API", description = "클라이언트 사용자 관련 API")
@RestController
@RequestMapping("api/v1/")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_SYSTEM_OPERATOR')")
class ClientUserController(
    private val clientUserService: ClientUserService
) {

    /**
     * 쿨라이언트 작업자 추가
     */
    @PostMapping("/client-user/worker")
    @Operation(
        summary = "클라이언트 작업자 생성",
        description = "클라이언트 작업자 생성 <br/><br/>",
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
        ]
    )
    fun createClientWorker(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @Validated @RequestBody requestDto: PostClientWorkerCreateDto.Request,
        @AuthenticationPrincipal memberId: Long
    ): ApiResult<*> {
        return ApiResult(
            PostClientWorkerCreateDto.Response(clientUserService.createClientWorker(requestDto, memberId, clientId))
        )
    }

    /**
     * 쿨라이언트 작업자 수정
     */
    @PutMapping("/client-user/{clientUserId}")
    @Operation(
        summary = "클라이언트 작업자 수정",
        description = "클라이언트 작업자 <br/><br/>",
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
        ]
    )
    fun updateClientWorker(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @Validated @RequestBody requestDto: PutClientWorkerUpdateDto.Request,
        @Parameter(description = "클라이언트 유저 ID") @PathVariable(name = "clientUserId") clientUserId: Long
    ): ApiResult<*> {
        clientUserService.updateClientWorker(requestDto, clientUserId)
        return ApiResult(null)
    }

    /**
     * 쿨라이언트 마스터 권한 위임
     */
    @PostMapping("/client-user/{clientUserId}/delegation")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    @Operation(
        summary = "마스터 권한 위임",
        description = "마스터 권한 위임 <br/><br/>",
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
        ]
    )
    fun updateDelegation(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @Parameter(description = "클라이언트 유저 ID") @PathVariable(name = "clientUserId") clientUserId: Long,
        @AuthenticationPrincipal memberId: Long
    ): ApiResult<*> {
        clientUserService.updateDelegation(memberId, clientId, clientUserId)
        return ApiResult(null)
    }



    /**
     * 쿨라이언트 사용자 조회
     */

    /**
     * 쿨라이언트 작업자 조회
     */
    @GetMapping("/client-user/{clientUserId}")
    @Operation(
        summary = "클라이언트 작업자 단건 조회",
        description = "클라이언트 작업자 단건 조회 <br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = GetClientWorkerDto.Response::class)
                )]
            ),
            ApiResponse(
                responseCode = "400", description = "유효하지 않은 데이터",
            ),
        ]
    )
    fun getClientUser(
        @Parameter(description = "클라이언트 ID", required = true) @RequestParam clientId: Long,
        @Parameter(description = "클라이언트 유저 ID", required = true) @PathVariable(name = "clientUserId") clientUserId: Long
    ): ApiResult<*> {
        return ApiResult(
            clientUserService.getClientWorker(clientUserId)
        )
    }

    /**
     * 쿨라이언트 작업자 리스트 페이징
     */
    @GetMapping("/client-user/worker/list")
    @Operation(
        summary = "작업자 리스트 페이징 조회",
        description = "작업자 리스트 페이징 <br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = GetClientWorkerListPageDto.Response::class))
                )]
            ),
            ApiResponse(
                responseCode = "400", description = "유효하지 않은 데이터",
            ),
        ]
    )
    fun getClientWorkerPage(
        @Parameter(description = "클라이언트 ID") @RequestParam clientId: Long,
        @Parameter(description = "정렬방향") @RequestParam(
            required = false,
            defaultValue = "ASC"
        ) sortDirection: Sort.Direction?,
        @Parameter(description = "페이지") @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "작업자 명") @RequestParam workerName: String?,
    ): ApiResult<*> {
        return ApiResult(
            clientUserService.getClientWorkerList(page, clientId, workerName, sortDirection)
        )
    }

    /**
     * 쿨라이언트 사용자 삭제
     */

    /**
     * 쿨라이언트 사용자 리스트 삭제
     */

}