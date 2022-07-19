package com.shoplworks.hada.svc.domain.member.controller

import com.shoplworks.hada.svc.common.filter.dto.LoginDto
import com.shoplworks.hada.svc.domain.member.dto.SignInDto
import com.shoplworks.hada.svc.domain.member.dto.SignUpDto
import com.shoplworks.hada.svc.domain.member.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * packageName : com.shoplworks.hada.svc.domain.member.controller
 * fileName : MemberAuthController
 * author : dave
 * date : 2022/07/16
 * description : 최초등록
 */
@RestController
class MemberAuthController(
    private val authService: AuthService
) {

    @PostMapping("/auth/signIn")
    @Operation(summary = "이메일 로그인",
        description = "로그인 <br/><br/>"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = SignUpDto.Response::class))]
            ),
            ApiResponse(
                responseCode = "400", description = "유효하지 않은 데이터",
            )

        ]
    )
    fun signIn(
        @Validated @RequestBody req: SignInDto.Request
    ): SignInDto.Response {

        return authService.signIn(req)
    }
}