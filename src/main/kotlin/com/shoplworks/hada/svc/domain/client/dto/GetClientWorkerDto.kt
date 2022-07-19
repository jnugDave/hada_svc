package com.shoplworks.hada.svc.domain.client.dto

import com.shoplworks.hada.svc.entity.ClientUserEntity
import io.swagger.v3.oas.annotations.media.Schema

/**
 * packageName : com.shoplworks.hada.svc.domain.client.dto
 * fileName : GetClientWorkerDto
 * author : dave
 * date : 2022/04/16
 * description : 최초등록
 */
class GetClientWorkerDto {

    @Schema(name = "GetClientWorkerDto.Response", description = "작업자 단건 조회 DTO")
    class Response {
        @Schema(description = "작업자 id", example = "12")
        var clientUserId: Long = 0

        @Schema(description = "작업자 이름", example = "su hua")
        lateinit var workerName: String

        @Schema(description = "휴대폰 번호", example = "01012341234")
        lateinit var workerPhone: String

        @Schema(description = "이메일", example = "nana@shoplworks.com")
        var workerEmail: String? = null

        @Schema(description = "메모", example = "구내 식당 청소 담당")
        var memo: String? = null

        fun of(clientUserEntity: ClientUserEntity): Response {

            this.clientUserId = clientUserEntity.clientUserId
            this.workerName = clientUserEntity.clientUserInfo?.userName ?: ""
            this.workerPhone = clientUserEntity.clientUserInfo?.userPhone ?: ""
            this.workerEmail = clientUserEntity.clientUserInfo?.userEmail
            this.memo = clientUserEntity.clientUserInfo?.memo

            return this
        }
    }
}