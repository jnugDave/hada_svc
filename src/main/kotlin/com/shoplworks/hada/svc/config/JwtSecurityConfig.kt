package com.shoplworks.hada.svc.config

import com.shoplworks.hada.svc.common.filter.JwtTokenFilter
import com.shoplworks.hada.svc.common.utils.JwtTokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * packageName : com.shoplworks.hada.svc.config
 * fileName : JwtSecurityConfig
 * author : dave
 * date : 2022/07/16
 * description : 최초등록
 */
class JwtSecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(http: HttpSecurity) {
        val customFilter = JwtTokenFilter(jwtTokenProvider)

        http
            .addFilterBefore(
                customFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
    }
}