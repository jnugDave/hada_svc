package com.shoplworks.hada.svc.common.oauth.oauthUserInfo

/**
 * packageName : com.example.oauth.common.oauth
 * fileName : OAuth2UserInfo
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */
abstract class OAuth2UserInfo(
    val attributes: Map<String, Any?>
) {

    abstract fun getId(): String?

    abstract fun getName(): String?

    abstract fun getEmail(): String?

    abstract fun getImageUrl(): String?

}