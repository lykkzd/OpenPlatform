package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.po.CustomerCarList;
import com.epicas.platform.domain.vo.CustomerCarListVO;

import java.util.List;

/**
 * 车辆信息的service层
 * @author liuyang
 * @date 2023年10月09日 16:35
 */
public interface CustomerCarListService extends IService<CustomerCarList> {

    /**
     * 根据车牌号获取车辆信息
     * @param carNumber
     * @return
     */
    CustomerCarListVO getCustomerCarListByCarNumber(String carNumber);


    /**
     * 根据车辆id列表获取车辆信息列表
     * @param carIdList
     * @return
     */
    List<CustomerCarListVO> findCustomerCarListVOListByCarIdList(List<Long> carIdList);
}
