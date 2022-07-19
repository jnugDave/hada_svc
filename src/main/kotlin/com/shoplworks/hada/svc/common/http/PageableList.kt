package com.shoplworks.hada.svc.common.http

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

/**
 * packageName : com.shoplworks.hada.svc.common.http
 * fileName : PageableList
 * author : dave
 * date : 2022/04/11
 * description : 최초등록
 */
class PageableList<T>(
    @Hidden
    private val page: Page<T>
) {

    @Schema
    val content: MutableList<T> = page.content

    @Schema(name = "isFirst", description = "첫 페이지 여부")
    val isFirst = page.isFirst

    @Schema(name = "isLast", description = "마지막 페이지 여부")
    val isLast = page.isLast

    @Schema(name = "isEmpty", description = "Empty 여부")
    val isEmpty = page.isEmpty

    @Schema(name = "numberOfElements", description = "현재 페이지의 갯수")
    val numberOfElements = page.numberOfElements

    @Schema(name = "number", description = "현재 페이지")
    val number = page.number

    @Schema(name = "totalPages", description = "전체 페이지")
    val totalPages = page.totalPages

    @Schema(name = "totalPages", description = "전체 수")
    val totalElements = page.totalElements

    @Schema(name = "totalPages", description = "전체 수")
    val size = page.size

}