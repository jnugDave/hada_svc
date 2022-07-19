package com.shoplworks.hada.svc.config

import com.shoplworks.hada.svc.common.filter.JwtTokenFilter
import com.shoplworks.hada.svc.common.oauth.CustomOAuth2UserService
import com.shoplworks.hada.svc.common.oauth.HttpCookieOAuth2AuthorizationRequestRepository
import com.shoplworks.hada.svc.common.oauth.OAuth2AuthenticationFailureHandler
import com.shoplworks.hada.svc.common.oauth.OAuth2AuthenticationSuccessHandler
import com.shoplworks.hada.svc.common.utils.JwtTokenProvider
import com.shoplworks.hada.svc.domain.member.service.impl.query.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


/**
 * packageName : com.example.oauth
 * fileName : WebSecurityConfig
 * author : dave
 * date : 2022/07/11
 * description : 최초등록
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConfig(
    val oAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    val customOAuth2UserService: CustomOAuth2UserService,
    val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
    val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {


        http
            .csrf().disable()
        http
            .authorizeHttpRequests()
            .antMatchers("/api/v1/**").authenticated()
            .antMatchers(
                "/auth/signIn",
                "/auth/signUp"
                ).permitAll()
            .anyRequest().permitAll()
            .and()
        http
            .addFilterBefore(
                JwtTokenFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java)


        http
            .oauth2Login()//oAuth2 설정 시작
            .authorizationEndpoint()
            .baseUri("/oauth2/authorize")
            .authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
            .and()
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")
            .and()
            .userInfoEndpoint().userService {
                customOAuth2UserService.loadUser(it)
            }
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler)
        http
            .cors().configurationSource(corsConfigurationSource())

        return http.build()
    }


    // CORS 허용 적용
    private fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration();

        configuration.allowedOrigins = listOf("*")
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}