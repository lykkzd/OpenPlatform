package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.dto.PositionFinalTransactionDTO;
import com.epicas.platform.domain.en.DataSource;
import com.epicas.platform.domain.po.CustomerCarList;
import com.epicas.platform.domain.po.PositionFinalTransaction;
import com.epicas.platform.domain.po.StaffCarWhiteList;
import com.epicas.platform.domain.vo.CustomerCarListVO;
import com.epicas.platform.exception.CarNumberNotExistException;
import com.epicas.platform.exception.DbException;
import com.epicas.platform.exception.OrgIdNotExistException;
import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.PositionFinalTransactionMapper;
import com.epicas.platform.service.CustomerCarListService;
import com.epicas.platform.service.PositionFinalTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liuyang
 * @date 2023年10月11日 10:39
 */
@Service
@RequiredArgsConstructor
public class PositionFinalTransactionServiceImpl extends ServiceImpl<PositionFinalTransactionMapper, PositionFinalTransaction> implements PositionFinalTransactionService {

    private final PositionFinalTransactionMapper positionFinalTransactionMapper;

    private final CustomerCarListService customerCarListService;

    /**
     * 记录车辆部位最后成交情况
     *
     * @param dto
     */
    @Transactional
    @Override
    public void recordFinalTransaction(PositionFinalTransactionDTO dto) {
        //1. 本地线程拿到orgId
        Long orgId = OrgIdHolder.getOrgId();
        //1.1 拿不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }
        //2. 根据carNumber去customer_car_list表中查到对应的车辆信息
        CustomerCarListVO customerCarListVO = customerCarListService.getCustomerCarListByCarNumber(dto.getCarNumber());
        //3 初始化carId
        Long carId;
        //4 判断是否存在
        if (customerCarListVO == null) {
            //4.1 不存在则新增
            CustomerCarList customerCarList = new CustomerCarList();
            customerCarList.setCarNumber(dto.getCarNumber());
            customerCarList.setInitOrgId(orgId);
            // TODO: 2023/10/11 汽车表插入数据时写死字段
            customerCarList.setFuelTypeId(1L);// 汽油类型
            customerCarList.setModelId(562L);// 车型Id
            customerCarListService.save(customerCarList);
            carId = customerCarList.getId();
        } else {
            //4.2 存在则获取carId
            carId = customerCarListVO.getId();
        }
        //5. 创建车辆部位最后成交情况对象插入数据库
        PositionFinalTransaction positionFinalTransaction = new PositionFinalTransaction();
        BeanUtils.copyProperties(dto, positionFinalTransaction);
        positionFinalTransaction.setOrgId(orgId);
        positionFinalTransaction.setCarId(carId);
        positionFinalTransaction.setDataSource(DataSource.THIRD_PARTY_CALL.getValue());
        positionFinalTransactionMapper.insert(positionFinalTransaction);
    }

    /**
     * 根据carId移除对应的车辆部位最后成交情况
     * @param dto
     */
    @Transactional
    @Override
    public void removePositionFinalTransaction(PositionFinalTransactionDTO dto) {
        //1. 参数校验
        if (dto.getCarNumber() == null) {
            throw new CarNumberNotExistException();
        }
        //2. 根据车牌号去customer_car_list表中查询对应的carId
        CustomerCarList customerCarList = customerCarListService.lambdaQuery()
                .eq(CustomerCarList::getCarNumber, dto.getCarNumber())
                .select(CustomerCarList::getId)
                .one();
        LambdaQueryWrapper<PositionFinalTransaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PositionFinalTransaction::getCarId, customerCarList.getId());
        positionFinalTransactionMapper.delete(queryWrapper);
    }
}
