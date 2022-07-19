package com.shoplworks.hada.svc.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


/**
 * packageName : com.shoplworks.hada.svc.config
 * fileName : WebConfig
 * author : dave
 * date : 2022/03/24
 * description : 최초등록
 */
@Configuration
class WebConfig: WebMvcConfigurer {

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.addBasenames("classpath:err-messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun getValidator(messageSource: MessageSource): LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource)
        return bean
    }
}