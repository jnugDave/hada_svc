package com.shoplworks.hada.svc.common.oauth

import com.shoplworks.hada.svc.common.utils.CookieUtils
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * packageName : com.example.oauth.common.oauth
 * fileName : OAuth2AuthenticationFailureHandler
 * author : dave
 * date : 2022/07/12
 * description : 최초등록
 */

/**
 * oAuth2 로그인 과정이 실패했을 때 처리하는 클래스
 */
@Component
class OAuth2AuthenticationFailureHandler(
    val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
): SimpleUrlAuthenticationFailureHandler() {

    val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException
    ) {
        var targetUrl: String = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue)
            .orElse("/")
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception.localizedMessage)
            .build().toUriString()
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}