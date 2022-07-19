package com.shoplworks.hada.svc.config

import com.shoplworks.hada.svc.entity.MemberEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

/**
 * packageName : com.shoplworks.hada.svc.config
 * fileName : JpaAuditingConfig
 * author : dave
 * date : 2022/03/27
 * description : 최초등록
 */
@EnableJpaAuditing
@Configuration
class JpaAuditingConfig {

    @Bean
    fun auditorAware(): CustomAuditorAware {
        return CustomAuditorAware()
    }

    class CustomAuditorAware: AuditorAware<Long> {
        override fun getCurrentAuditor(): Optional<Long> {
            val authentication = SecurityContextHolder.getContext().authentication

            if(authentication.principal.equals("anonymousUser")) {
                return Optional.empty()
            }

            return Optional.of(authentication.principal as Long)
        }
    }
}