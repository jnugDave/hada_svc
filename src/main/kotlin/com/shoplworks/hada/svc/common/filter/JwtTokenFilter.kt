package com.shoplworks.hada.svc.common.filter

import com.shoplworks.hada.svc.common.exception.CustomException
import com.shoplworks.hada.svc.common.utils.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * packageName : com.shoplworks.hada.svc.common.filter
 * fileName : JwtTokenFilter
 * author : dave
 * date : 2022/07/16
 * description : 최초등록
 */
class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //각각 클라이언트마다 멤버의 권한이 다르기
        // 때문에 요청마다 클라이언트 아이디를 받아야 한다
        val clientId: String? = request.getParameter("clientId")

        //header 에서 토큰 획득
        val token = jwtTokenProvider.resolveToken(request)

        try {
            if(token != null && jwtTokenProvider.validateToken(token)) {
                //토큰에서 memberId 추출
                val memberId = jwtTokenProvider.parseMemberId(token)

                val authentication = jwtTokenProvider.getAuthentication(memberId.toLong(), clientId?.toLong())
                //SecurityContextHolder 에 저장
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: CustomException) {
            SecurityContextHolder.clearContext()
            response.sendError(e.errorCode.status, e.message)
            return
        }

        filterChain.doFilter(request, response)
    }
}