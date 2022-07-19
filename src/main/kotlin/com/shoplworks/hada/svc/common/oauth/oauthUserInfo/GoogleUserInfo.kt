package com.shoplworks.hada.svc.common.oauth.oauthUserInfo

/**
 * packageName : com.example.oauth.common.oauth.oauthUserInfo
 * fileName : GoogleUserInfo
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */
class GoogleUserInfo(attributes: Map<String, Any?>): OAuth2UserInfo(attributes) {
    override fun getId(): String? {
        return attributes["sub"] as String
    }

    override fun getName(): String? {
        return attributes["name"] as String
    }

    override fun getEmail(): String? {
        return attributes["email"] as String
    }

    override fun getImageUrl(): String? {
        return attributes["picture"] as String
    }
}