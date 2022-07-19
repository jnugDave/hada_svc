package com.shoplworks.hada.svc.common.oauth

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.utils.CookieUtils
import com.shoplworks.hada.svc.common.utils.JwtTokenProvider
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * packageName : com.example.oauth.common.oauth
 * fileName : OAuth2AuthenticationSuccessHandler
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */

/**
 * oAuth2 로그인 과정이 성공했을 때 처리하는 클래스
 */
@Component
class OAuth2AuthenticationSuccessHandler(
    val tokenUtils: JwtTokenProvider,
    val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
): SimpleUrlAuthenticationSuccessHandler() {

    val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"

    /**
     * 인증 완료시 실행되는 메서드
     */
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authentication: Authentication
    ) {

        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            return
        }
        //쿠키에 있는 OAuth2UserRequest 를 지워준다.
        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    //token 을 생성하고 이를 포함한 프론트엔드로의 uri 를 생성한다.
    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): String? {
        val redirectUri: Optional<String> = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue)

        //redirect uri 유효성 검사
        if (!redirectUri.isPresent) {
            throw CustomException(ErrorCode.BAD_ARGUMENT, "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
        }
        val targetUrl: String = redirectUri.orElse(defaultTargetUrl)

        //토큰 생성
        val token: String = tokenUtils.generateToken((authentication.principal as UserPrincipal).name)
        //uri 생성 및 파라미터에 토큰 추가
        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("token", token)
            .build().toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest?, response: HttpServletResponse?) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }


}