package com.shoplworks.hada.svc.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * packageName : com.shoplworks.hada.svc.config
 * fileName : QuerydslConfig
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@Configuration
class QuerydslConfig(
    @PersistenceContext
    val entityManager: EntityManager
) {
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}