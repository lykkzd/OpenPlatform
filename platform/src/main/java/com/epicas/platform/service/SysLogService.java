package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.dto.LogDTO;
import com.epicas.platform.domain.po.Log;

/**
 * @author liuyang
 * @date 2023年10月09日 11:07
 */
public interface SysLogService extends IService<Log> {
    /**
     * 新增日志
     * @param logDTO
     */
    void addLog(LogDTO logDTO);
}
