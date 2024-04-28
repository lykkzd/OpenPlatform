package com.epicas.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.epicas.platform.domain.po.Dossier;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对应档案逻辑的映射层
 * @author liuyang
 * @date 2023年10月08日 18:05
 */
@Mapper
public interface DossierMapper extends BaseMapper<Dossier> {
}
