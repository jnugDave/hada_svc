package com.shoplworks.hada.svc.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.SpringDocUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.annotation.AuthenticationPrincipal

/**
 * packageName : com.shoplworks.hada.svc.config
 * fileName : OpenApiConfig
 * author : dave
 * date : 2022/03/23
 * description : 최초등록
 */
@Configuration
class OpenApiConfig {

    @Value("\${springdoc.server:http://localhost:\${server.port}}")
    private lateinit var serverUrl: String

    @Bean
    fun openApi(): OpenAPI {

        //spring security 어노테이션이 붙은 변수는 스웨거에서 무시하도록 설정
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthenticationPrincipal::class.java)

        val server = Server()
        server.url = serverUrl

        return OpenAPI()
            .info(Info().title("HADA Demo App API").version("v1.0"))
            .components(Components().addSecuritySchemes("bearer-key", SecurityScheme().type(SecurityScheme.Type.HTTP)
                .scheme("bearer").bearerFormat("JWT")))
                    .servers(listOf(server))
    }


}