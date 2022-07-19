package com.shoplworks.hada.svc.common.enums

/**
 * packageName : com.shoplworks.hada.svc.common.enums
 * fileName : Sort
 * author : dave
 * date : 2022/04/11
 * description : 최초등록
 */
class Sort {

    enum class Direction {
        ASC, DESC
    }

    enum class Checklist {
        CHECKLIST_NAME, CHECK_TARGET_COUNT
    }

    enum class CheckTarget {
        LAST_CHECK_DATE, CHECK_TARGET_NAME
    }
}