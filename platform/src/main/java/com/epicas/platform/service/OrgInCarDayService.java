package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.po.OrgInCarDay;
import com.epicas.platform.domain.dto.OrgInCarDayDTO;

import java.util.List;

/**
 * @author liuyang
 * @date 2023年10月07日 17:35
 */
public interface OrgInCarDayService extends IService<OrgInCarDay> {
    /**
     * 保存进店台次和保养基数
     * @param orgInCarDayDTOList
     */
    void saveOrgInCarDayList(List<OrgInCarDayDTO> orgInCarDayDTOList);
}
