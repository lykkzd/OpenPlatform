package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.dto.LogDTO;
import com.epicas.platform.domain.po.Log;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.SysLogMapper;
import com.epicas.platform.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuyang
 * @date 2023年10月09日 11:17
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, Log> implements SysLogService {

    private final SysLogMapper sysLogMapper;

    @Transactional
    @Override
    public void addLog(LogDTO logDTO) {
        //1. 将logDTO转换为Log
        Log log = new Log();
        BeanUtils.copyProperties(logDTO,log);
        //2. 调用mapper的insert方法
        sysLogMapper.insert(log);
    }
}
