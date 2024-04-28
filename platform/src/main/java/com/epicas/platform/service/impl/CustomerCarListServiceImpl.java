package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.po.CustomerCarList;
import com.epicas.platform.domain.vo.CustomerCarListVO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.CustomerCarListMapper;
import com.epicas.platform.service.CustomerCarListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuyang
 * @date 2023年10月09日 16:43
 */
@Service
@RequiredArgsConstructor
public class CustomerCarListServiceImpl extends ServiceImpl<CustomerCarListMapper, CustomerCarList> implements CustomerCarListService {

    private final CustomerCarListMapper customerCarListMapper;

    /**
     * 根据车牌号查询车辆信息（若查不到则返回空）
     * @param carNumber
     * @return
     */

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @EpicasLog(value = "根据车牌号查询车辆信息（若查不到则返回空）", paramIndex = {0})
    @Override
    public CustomerCarListVO getCustomerCarListByCarNumber(String carNumber) {

        LambdaQueryWrapper<CustomerCarList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerCarList::getCarNumber,carNumber)
                .select(CustomerCarList::getId);
        CustomerCarList customerCarList = customerCarListMapper.selectOne(queryWrapper);

        //判断是否查到车辆信息
        if (customerCarList == null) {
            //查不到则返回空
            return null;
        } else {
            //查得到则po转vo返回
            CustomerCarListVO customerCarListVO = new CustomerCarListVO();
            BeanUtils.copyProperties(customerCarList,customerCarListVO);
            return customerCarListVO;
        }
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @EpicasLog(value = "根据carId列表查询车牌号", paramIndex = {0})
    @Override
    public List<CustomerCarListVO> findCustomerCarListVOListByCarIdList(List<Long> carIdList) {
        LambdaQueryWrapper<CustomerCarList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CustomerCarList::getId,carIdList)
                .select(CustomerCarList::getCarNumber);

        return customerCarListMapper.selectList(queryWrapper).stream()
                .map(customerCarListDO -> {
                    CustomerCarListVO customerCarListVO = new CustomerCarListVO();
                    BeanUtils.copyProperties(customerCarListDO,customerCarListVO);
                    return customerCarListVO;
                }).collect(Collectors.toList());
    }
}
