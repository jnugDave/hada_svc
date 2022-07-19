package com.shoplworks.hada.svc.domain.client.controller

import com.shoplworks.hada.svc.common.http.ApiResult
import com.shoplworks.hada.svc.domain.client.dto.PostClientCreateDto
import com.shoplworks.hada.svc.domain.client.service.ClientService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
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
 * fileName : ClientController
 * author : dave
 * date : 2022/03/27
 * description : 최초등록
 */
@Tag(name = "클라이언트 API", description = "클라이언트 관련 API")
@RestController
@RequestMapping("api/v1/")
class ClientController(
    private val clientService: ClientService
) {

    /**
     * 클라이언트 생성
     */
    @PostMapping("/client")
    @Operation(summary = "클라이언트 생성",
        description = "클라이언트 생성 <br/><br/>",
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
    fun createClient(
        @Validated @RequestBody requestDto: PostClientCreateDto.Request,
        @AuthenticationPrincipal memberId: Long
    ): ApiResult<*> {
        val clientId = clientService.createClient(requestDto, memberId)
        return ApiResult(
            PostClientCreateDto.Response(clientId)
        )
    }

    /**
     * 클라이언트 삭제
     */
    @DeleteMapping("/client")
    @PreAuthorize("hasRole('ROLE_OWNER')") //owner 권한만 삭제 가능
    @Operation(summary = "클라이언트 삭제",
        description = "클라이언트 삭제 <br/><br/>",
        security = [SecurityRequirement(name = "bearer-key")]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
            ),
            ApiResponse(
                responseCode = "404002", description = "클라이언트가 존재하지 않음",
            ),
        ]
    )
    fun deleteClient(
        @RequestParam @Parameter(description = "클라이언트 ID") clientId: Long,
        @AuthenticationPrincipal memberId: Long
    ): ApiResult<*> {
        clientService.deleteClient(clientId)
        return ApiResult(null)
    }
}