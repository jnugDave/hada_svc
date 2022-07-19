package com.shoplworks.hada.svc.entity.common.converter

import com.shoplworks.hada.svc.vo.CheckCycleAttrVo
import com.shoplworks.hada.svc.vo.CheckTargetAttrVo
import com.shoplworks.hada.svc.vo.ChecklistAttrVo
import com.shoplworks.hada.svc.vo.ChecklistItemAttrVo

/**
 * packageName : com.shoplworks.hada.svc.entity.common.converter
 * fileName : ModelConverter
 * author : dave
 * date : 2022/03/31
 * description : 최초등록
 */
class ModelConverter {
    class ChecklistAttrConverter: GenericJsonConverter<ChecklistAttrVo>(ChecklistAttrVo::class.java)
    class CheckTargetAttrConverter: GenericJsonConverter<CheckTargetAttrVo>(CheckTargetAttrVo::class.java)
    class CheckCycleAttrConverter: GenericJsonConverter<CheckCycleAttrVo>(CheckCycleAttrVo::class.java)
    class ChecklistItemAttrConverter: GenericJsonConverter<ChecklistItemAttrVo>(ChecklistItemAttrVo::class.java)
}