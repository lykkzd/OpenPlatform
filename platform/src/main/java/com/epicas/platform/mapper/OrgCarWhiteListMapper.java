package com.epicas.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.epicas.platform.domain.po.StaffCarWhiteList;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工车辆白名单的映射层
 * @author liuyang
 * @date 2023年10月10日 15:23
 */
@Mapper
public interface OrgCarWhiteListMapper extends BaseMapper<StaffCarWhiteList> {
}
