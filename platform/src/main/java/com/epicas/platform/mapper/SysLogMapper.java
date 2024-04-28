package com.epicas.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.epicas.platform.domain.po.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuyang
 * @date 2023年10月09日 11:18
 */
@Mapper
public interface SysLogMapper extends BaseMapper<Log> {
}
