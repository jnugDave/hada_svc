package com.shoplworks.hada.svc.common.oauth.oauthUserInfo

/**
 * packageName : com.example.oauth.common.oauth.oauthUserInfo
 * fileName : KakaoUserInfo
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */
class EmailUserInfo(attributes: Map<String, Any?>): OAuth2UserInfo(attributes) {


    private var id: Int = attributes["id"] as Int

    override fun getId(): String {
        return this.id.toString()
    }

    override fun getName(): String? {
        return ""
    }

    override fun getEmail(): String? {
        return ""
    }

    override fun getImageUrl(): String? {
        return ""
    }


}