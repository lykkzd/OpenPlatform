package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.po.OrgPlanList;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.OrgPlanListMapper;
import com.epicas.platform.service.OrgPlanListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 检测方案的service层实现类
 * @author liuyang
 * @date 2023年10月10日 14:01
 */
@Service
@RequiredArgsConstructor
public class OrgPlanListServiceImpl extends ServiceImpl<OrgPlanListMapper, OrgPlanList> implements OrgPlanListService {

    private final OrgPlanListMapper orgPlanListMapper;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @EpicasLog(value = "查找有效的检测方案", paramIndex = {0})
    @Override
    public List<Long> findPlanIdList(Long orgId, Short isEffective) {
        LambdaQueryWrapper<OrgPlanList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrgPlanList::getOrgId,orgId).eq(OrgPlanList::getIsEffective,isEffective).select(OrgPlanList::getPlanId);
        return orgPlanListMapper.selectList(queryWrapper)
                .stream()
                .map(OrgPlanList::getPlanId)
                .distinct()
                .collect(Collectors.toList());
    }
}
