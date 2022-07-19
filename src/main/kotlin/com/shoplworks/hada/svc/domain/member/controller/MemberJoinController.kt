package com.shoplworks.hada.svc.domain.member.controller

import com.shoplworks.hada.svc.common.http.ApiResult
import com.shoplworks.hada.svc.domain.member.dto.SignUpDto
import com.shoplworks.hada.svc.domain.member.service.AuthService
import com.shoplworks.hada.svc.domain.member.service.QueryMemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * packageName : com.shoplworks.hada.svc.domain.member.controller
 * fileName : MemberJoinController
 * author : dave
 * date : 2022/03/24
 * description : 최초등록
 */
@Tag(name = "맴버 회원가입 API", description = "맴버 회원가입 관련 API")
@RestController
class MemberJoinController(
    private val authService: AuthService,
    private val memberQueryService: QueryMemberService
) {

    @PostMapping("/auth/signUp")
    @Operation(summary = "이메일 회원가입",
        description = "회원가입 <br/><br/>"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = SignUpDto.Response::class))]
            ),
            ApiResponse(
                responseCode = "400", description = "유효하지 않은 데이터",
            ),
            ApiResponse(
                responseCode = "409001", description = "이메일 중복",
            ),
        ]
    )
    fun signUp(
        @Validated @RequestBody req: SignUpDto.Request
    ): ApiResult<*> {
        val memberId = authService.signUp(req)
        val result = memberQueryService.getMemberInfo(memberId)
       return ApiResult(result)
    }
}