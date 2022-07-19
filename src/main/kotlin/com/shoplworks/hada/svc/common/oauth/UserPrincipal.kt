package com.shoplworks.hada.svc.common.oauth

import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*


/**
 * packageName : com.example.oauth.common.oauth
 * fileName : UserPrincipal
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */


class UserPrincipal: OAuth2User, UserDetails {
    private var id: Long = 0
    private var email: String? = null
    private var password: String? = null
    private var authorities: Collection<GrantedAuthority>? = null
    private val attributes: Map<String?, Any?>? = null

    constructor(id: Long, email: String, password: String, authorities: Collection<GrantedAuthority>?) {
        this.id = id
        this.email = email
        this.password = password
        this.authorities = authorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return authorities
    }

    override fun getAttributes(): Map<String?, Any?>? {
        return attributes
    }

    override fun getName(): String {
        return id.toString()
    }


    companion object {
        fun create(memberEntity: MemberEntity): UserPrincipal {
            val authorities: List<GrantedAuthority> = Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))

            return UserPrincipal(
                memberEntity.memberId!!,
                memberEntity.email,
                memberEntity.password,
                authorities
            )
        }
    }
}