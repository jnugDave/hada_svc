package com.shoplworks.hada.svc.common.oauth


import com.shoplworks.hada.svc.common.enums.AuthProvider
import com.shoplworks.hada.svc.common.oauth.oauthUserInfo.OAuth2UserInfo
import com.shoplworks.hada.svc.domain.member.repository.MemberRepository
import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


/**
 * packageName : com.example.oauth.common.oauth
 * fileName : CustomOAuth2UserService
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */

/**
 * spring oAuth2 에서 제공하는 OAuth2User 을 가공하여 OAuth2UserInfo 로 만들고
 * OAuth2UserInfo 에 Email 이 있는지 검사와, A라는 플랫폼으로 가입이 되어있는데, B플랫폼으로 가입하려는 경우 검사를 진행한다
 * email 이 존재하지 않는 경우 insert 하며, UserPrincipal 을 반환한다.
 */
@Service
class CustomOAuth2UserService(
    val memberRepository: MemberRepository
): DefaultOAuth2UserService() {

    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(oAuth2UserRequest)

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {

        val registrationId = oAuth2UserRequest.clientRegistration.registrationId
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.attributes)

        if(oAuth2UserInfo.getEmail().isNullOrEmpty()) {
            throw Exception()
        }

        val userOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
        var member: MemberEntity? = null

        //이메일로 가입한 계정이 이미 존재하는 경우
        if (userOptional.isPresent) {
            member = userOptional.get()

            //가져온 유저의 공급자명과 넘어온 공급자명이 다른 경우
            if (member.provider != AuthProvider.valueOf(registrationId)) {
                // 이미 다른 공급자가 존재하기 진행할 수 없다.
                throw Exception("Looks like you're signed up with " +
                        member.provider + " account. Please use your " + member.provider +
                        " account to login.")
            }

//            //accountEntity 업데이트
//            member = updateExistingUser(member, oAuth2UserInfo)
        }
        //존재하지 않는 경우
        else {

            //accountEntity 생성
            member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo)

        }

        return UserPrincipal.create(member)

    }

    /**
     * DB 에 AccountEntity 저장
     */
    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): MemberEntity {

        return memberRepository.save(
            MemberEntity(
                email = oAuth2UserInfo.getEmail()!!,
                password = getRandomString(10), //비밀번호 랜덤 문자열 설정
                nickname = oAuth2UserInfo.getName()!!,
                phone = null,
                provider = AuthProvider.valueOf(oAuth2UserRequest.clientRegistration.registrationId),
                imageUrl = oAuth2UserInfo.getImageUrl(),
            )
        )
    }

    /**
     * 비밀번호에 넣기 위한 임의의 문자열 생성
     */
    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

//    /**
//     * DB 에 AccountEntity 업데이트
//     */
//    private fun updateExistingUser(accountEntity: MemberEntity, oAuth2UserInfo: OAuth2UserInfo?): AccountEntity {
//        accountEntity.updateNameAndImage(oAuth2UserInfo?.getName(), oAuth2UserInfo?.getImageUrl())
//        return userRepository.save(accountEntity)
//    }
}