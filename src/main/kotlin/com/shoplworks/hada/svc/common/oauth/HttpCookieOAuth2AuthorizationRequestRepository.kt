package com.shoplworks.hada.svc.common.oauth

import com.nimbusds.oauth2.sdk.util.StringUtils
import com.shoplworks.hada.svc.common.utils.CookieUtils
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * packageName : com.example.oauth.common.oauth
 * fileName : HttpCookieOAuth2AuthorizationReuestRepository
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */

/**
 * oAuth2 프로토콜은 CSRF 공격을 방지하기 위해 처음에 어플리케이션 서버에서 oAuth2 공급자에게 state 매게 변수를 전송하고
 * oAuth2 공급자는 다시 state 값을 반환하여 초기의 state 값과 비교를 한다.
 * HttpCookieOAuth2AuthorizationRequestRepository 는 초기의 state 매개 변수 값을 cookie 에 저장하기 위해 필요한 클래스이다.
 * 추가적으로 front 서버로 리다이렉트할 redirect_uri 도 쿠키에 저장한다.
 */
@Component
class HttpCookieOAuth2AuthorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request"
    val REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri"
    private val cookieExpireSeconds = 180

    /**
     * cookie에 저장되어있던 authorizationRequest 들을 가져온다
     */
    override fun loadAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            .map { cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest::class.java) }
            .orElse(null)
    }

    /**
     * 플랫폼으로 보내기 위한 Request를 'oauth2_auth_request' 라는 cookie에 저장한다.
     */
    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response)
            return
        }
        //authorizationRequest 를 쿠키에 저장
        CookieUtils.addCookie(
            response,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtils.serialize(authorizationRequest),
            cookieExpireSeconds
        )

        //redirect 파라미터를 쿠키에 넣어준다
        val redirectUriAfterLogin = request?.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(
                response,
                REDIRECT_URI_PARAM_COOKIE_NAME,
                redirectUriAfterLogin,
                cookieExpireSeconds)
        }
    }

    /**
     * Remove 를 재정의 해서 cookie 를 가져오고 remove는 successHandler 또는 failureHandler 에서 할 수 있도록 한다.
     */
    override fun removeAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest {
        return this.loadAuthorizationRequest(request)
    }

    /**
     * 쿠키 데이터 삭제
     */
    fun removeAuthorizationRequestCookies(request: HttpServletRequest?, response: HttpServletResponse?) {
        CookieUtils.deleteCookie(request!!, response!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }

}