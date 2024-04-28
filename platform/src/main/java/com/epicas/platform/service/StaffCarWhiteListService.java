package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.dto.AddOrgCarWhiteListDTO;
import com.epicas.platform.domain.dto.UpdateStaffCarWhiteListDTO;
import com.epicas.platform.domain.po.StaffCarWhiteList;

/**
 * 员工车辆白名单的service层
 * @author liuyang
 * @date 2023年10月10日 15:24
 */
public interface StaffCarWhiteListService extends IService<StaffCarWhiteList> {
    /**
     * 新增机构车辆
     * @param addOrgCarWhiteListDTO
     */
    void addOrgCarWhiteList(AddOrgCarWhiteListDTO addOrgCarWhiteListDTO);

    /**
     * 根据carId移除员工车辆白名单
     * @param updateStaffCarWhiteListDTO
     */
    void updateStaffCarWhiteList(UpdateStaffCarWhiteListDTO updateStaffCarWhiteListDTO);
}
