package com.shoplworks.hada.svc.common.oauth

import com.shoplworks.hada.svc.common.enums.AuthProvider
import com.shoplworks.hada.svc.common.oauth.oauthUserInfo.EmailUserInfo
import com.shoplworks.hada.svc.common.oauth.oauthUserInfo.GoogleUserInfo
import com.shoplworks.hada.svc.common.oauth.oauthUserInfo.KakaoUserInfo
import com.shoplworks.hada.svc.common.oauth.oauthUserInfo.OAuth2UserInfo


/**
 * packageName : com.example.oauth.common.oauth
 * fileName : OAuth2UserInfoFactory
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */
class OAuth2UserInfoFactory {

    companion object {
        fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any?>): OAuth2UserInfo {

            return when(AuthProvider.valueOf(registrationId)) {
                AuthProvider.email -> {
                    EmailUserInfo(attributes)
                }
                AuthProvider.google -> {
                    GoogleUserInfo(attributes)
                }
                AuthProvider.kakao -> {
                    KakaoUserInfo(attributes)
                }
            }
        }
    }
}