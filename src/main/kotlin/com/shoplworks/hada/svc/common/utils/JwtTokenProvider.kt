package com.shoplworks.hada.svc.common.utils

import com.shoplworks.hada.svc.common.enums.ErrorCode
import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.oauth.UserPrincipal
import com.shoplworks.hada.svc.domain.member.service.impl.query.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

/**
 * packageName : com.shoplworks.hada.svc.common.utils
 * fileName : JwtTokenProvider
 * author : dave
 * date : 2022/07/16
 * description : 최초등록
 */
@Component
class JwtTokenProvider(
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {

    @Value("\${jwt.token.secretKey}")
    private lateinit var secretKey: String

    @Value("\${jwt.token.expTime}")
    private var expTime: Int = 0

    /**
     * 토큰 생성
     */
    fun generateToken(memberId: String): String {

        val now = Date()
        val expiryDate = Date(now.time + expTime)

        return Jwts.builder()
            .setSubject(memberId)
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSignInKey(secretKey: String): Key? {
        val keyBytes = secretKey.toByteArray(StandardCharsets.UTF_8)

        return Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * authentication 반환
     */
    fun getAuthentication(memberId: Long, clientId: Long?): Authentication {
        val userDto = userDetailsServiceImpl.getUserDetailsById(memberId, clientId)
        return UsernamePasswordAuthenticationToken(
            userDto.id,
            null,
            //유저 권한
            mutableListOf(GrantedAuthority { userDto.userType.toString() })
        )
    }

    /**
     * Header 로부터 bearer 토큰을 가져온다
     */
    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }

        return null
    }

    /**
     * 토큰에서 username 파싱
     */
    fun parseMemberId(token: String): String {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey(secretKey))
            .build().parseClaimsJws(token).body.subject
    }

    /**
     * 토큰을 검증
     */
    fun validateToken(token: String): Boolean {

        try {

             Jwts.parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token)

            return true

        } catch (e: JwtException) {
            throw CustomException(ErrorCode.MEMBER_UNAUTHORIZED)
        }
    }

    /**
     * 모든 Claims 조회
     */
    private fun getAllClaims(token: String): Claims {

        val jwtSecret: SecretKey? = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

        return Jwts.parserBuilder()
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(token)
            .body
    }
}

