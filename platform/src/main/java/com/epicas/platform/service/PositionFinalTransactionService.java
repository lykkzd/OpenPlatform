package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.dto.PositionFinalTransactionDTO;
import com.epicas.platform.domain.po.PositionFinalTransaction;

/**
 * @author liuyang
 * @date 2023年10月11日 10:38
 */
public interface PositionFinalTransactionService extends IService<PositionFinalTransaction> {
    /**
     * 记录车辆部位最后成交情况
     * @param dto
     */
    void recordFinalTransaction(PositionFinalTransactionDTO dto);

    /**
     * 删除车辆部位最后成交情况
     * @param dto
     */
    void removePositionFinalTransaction(PositionFinalTransactionDTO dto);
}
