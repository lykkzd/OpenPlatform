package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.dto.AddOrgCarWhiteListDTO;
import com.epicas.platform.domain.dto.UpdateStaffCarWhiteListDTO;
import com.epicas.platform.domain.po.CustomerCarList;
import com.epicas.platform.domain.po.StaffCarWhiteList;
import com.epicas.platform.domain.vo.CustomerCarListVO;
import com.epicas.platform.exception.DbException;
import com.epicas.platform.exception.OrgIdNotExistException;
import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.OrgCarWhiteListMapper;
import com.epicas.platform.service.CustomerCarListService;
import com.epicas.platform.service.StaffCarWhiteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工车辆白名单的service层实现类
 * @author liuyang
 * @date 2023年10月10日 15:25
 */
@Service
@RequiredArgsConstructor
public class StaffCarWhiteListServiceImpl extends ServiceImpl<OrgCarWhiteListMapper, StaffCarWhiteList> implements StaffCarWhiteListService {

    private final OrgCarWhiteListMapper orgCarWhiteListMapper;

    private final CustomerCarListService customerCarListService;

    /**
     * 添加员工车辆白名单
     * @param addOrgCarWhiteListDTO
     */
    @Transactional
    @Override
    public void addOrgCarWhiteList(AddOrgCarWhiteListDTO addOrgCarWhiteListDTO) {
        //1. 本地线程拿到orgId
        Long orgId = OrgIdHolder.getOrgId();
        //1.1 拿不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }
        //2. 根据carNumber去customer_car_list表中查到对应的车辆信息
        CustomerCarListVO customerCarListVO = customerCarListService.getCustomerCarListByCarNumber(addOrgCarWhiteListDTO.getCarNumber());
        //3 初始化carId
        Long carId;
        //4 判断是否存在
        if (customerCarListVO == null) {
            //4.1 不存在则新增
            CustomerCarList customerCarList = new CustomerCarList();
            customerCarList.setCarNumber(addOrgCarWhiteListDTO.getCarNumber());
            customerCarList.setFrameNumber(addOrgCarWhiteListDTO.getFrameNumber());
            customerCarList.setInitOrgId(orgId);
            // TODO: 2023/10/11 汽车表插入数据时写死字段
            customerCarList.setFuelTypeId(1L);// 汽油类型
            customerCarList.setModelId(562L);// 车型Id
            customerCarListService.save(customerCarList);
            carId = customerCarList.getId();
        }else {
            //4.2 存在则获取carId
            carId = customerCarListVO.getId();
        }
        //5. 创建员工车辆白名单对象插入数据库
        StaffCarWhiteList staffCarWhiteList = new StaffCarWhiteList();
        staffCarWhiteList.setOrgId(orgId);
        staffCarWhiteList.setCarId(carId);
        //6. 设置createTime和updateTime
        staffCarWhiteList.setCreateTime(System.currentTimeMillis());
        staffCarWhiteList.setUpdateTime(System.currentTimeMillis());
        //7. 调用mapper的insert方法
        orgCarWhiteListMapper.insert(staffCarWhiteList);
    }

    /**
     * 根据carId移除员工车俩白名单
     * @param updateStaffCarWhiteListDTO
     */
    @Transactional
    @Override
    public void updateStaffCarWhiteList(UpdateStaffCarWhiteListDTO updateStaffCarWhiteListDTO) {
        CustomerCarList customerCarList = customerCarListService.lambdaQuery()
                .eq(CustomerCarList::getCarNumber, updateStaffCarWhiteListDTO.getCarNumber())
                .one();
        boolean success = lambdaUpdate()
                .set(StaffCarWhiteList::getIsDeleted, updateStaffCarWhiteListDTO.getIsDeleted())
                .eq(StaffCarWhiteList::getCarId, customerCarList.getId())
                .update();
        if(!success){
            throw new DbException();
        }
    }
}
