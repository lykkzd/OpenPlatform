package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.po.OrgPlanList;

import java.util.List;

/**
 * 检测方案的service层
 * @author liuyang
 * @date 2023年10月10日 13:59
 */
public interface OrgPlanListService extends IService<OrgPlanList> {
    /**
     * 查询机构的所有有效的计划id
     * @param orgId
     * @param isEffective
     * @return
     */
    List<Long> findPlanIdList(Long orgId, Short isEffective);
}
