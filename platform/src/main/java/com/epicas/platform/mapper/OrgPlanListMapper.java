package com.epicas.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.epicas.platform.domain.po.OrgPlanList;
import org.apache.ibatis.annotations.Mapper;

/**
 * 检测方案的映射层
 * @author liuyang
 * @date 2023年10月10日 14:02
 */
@Mapper
public interface OrgPlanListMapper extends BaseMapper<OrgPlanList> {
}
