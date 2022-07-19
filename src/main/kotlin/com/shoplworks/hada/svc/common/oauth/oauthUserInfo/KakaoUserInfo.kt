package com.shoplworks.hada.svc.common.oauth.oauthUserInfo

/**
 * packageName : com.example.oauth.common.oauth.oauthUserInfo
 * fileName : KakaoUserInfo
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */
class KakaoUserInfo(attributes: Map<String, Any?>): OAuth2UserInfo(attributes) {


    private var id: Int = attributes["id"] as Int

    override fun getId(): String {
        return this.id.toString()
    }

    override fun getName(): String? {
        return ((attributes["kakao_account"] as Map<String, Any?>)["profile"] as Map<String, Any?>)["nickname"] as String?
    }

    override fun getEmail(): String? {
        return ((attributes["kakao_account"] as Map<String, Any?>))["email"] as String?
    }

    override fun getImageUrl(): String? {
        return (((attributes["kakao_account"] as Map<String, Any?>))["profile"] as Map<String, Any?>)["thumbnail_image_url"] as String?
    }


}