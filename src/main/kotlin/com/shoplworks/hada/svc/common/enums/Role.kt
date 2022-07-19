package com.shoplworks.hada.svc.common.enums

/**
 * packageName : com.shoplworks.hada.svc.common.enums
 * fileName : Role
 * author : dave
 * date : 2022/03/27
 * description : 최초등록
 */
enum class Role {

    /**
     * 관리자 최고 정책
     */
    ADMIN,

    /**
     * 마스터 계정 정책
     */
    MASTER,

    /**
     * 시스템 운영자 기본 정책
     */
    SYSTEM_OPERATOR,

    /**
     * 관리자 기본 정책
     */
    MANAGER,

    /**
     * 작업자 기본 정책
     */
    WORKER
}