package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.po.OrgInCarDay;
import com.epicas.platform.domain.dto.OrgInCarDayDTO;
import com.epicas.platform.exception.DbException;
import com.epicas.platform.exception.OrgIdNotExistException;
import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.OrgInCarDayMapper;
import com.epicas.platform.service.OrgInCarDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuyang
 * @date 2023年10月07日 17:36
 */
@Service
@RequiredArgsConstructor
public class OrgInCarDayServiceImpl extends ServiceImpl<OrgInCarDayMapper, OrgInCarDay> implements OrgInCarDayService {

    private final OrgInCarDayMapper orgInCarDayMapper;

    /**
     * 保存进店台次和保养基数
     * @param orgInCarDayDTOList
     */
    @Transactional
    @Override
    public void saveOrgInCarDayList(List<OrgInCarDayDTO> orgInCarDayDTOList) {
        if (orgInCarDayDTOList.size() > 0) {
            throw new DbException();
        }
        //1. 本地线程获取orgId
        Long orgId = OrgIdHolder.getOrgId();
        //2. 获取不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }

        orgInCarDayDTOList.stream().forEach(orgInCarDayDTO -> {
            //3. 去数据库查询，看是否有该条数据
            OrgInCarDay orgInCarDay = this.lambdaQuery()
                    .eq(OrgInCarDay::getOrgId, orgId)
                    .eq(OrgInCarDay::getDayIndex, orgInCarDayDTO.getDayIndex())
                    .one();
            if (orgInCarDay == null) {
                //3.1 数据库中没有该条数据，则执行插入操作
                orgInCarDay = new OrgInCarDay();
                BeanUtils.copyProperties(orgInCarDayDTO, orgInCarDay);
                orgInCarDay.setUpdateTime(System.currentTimeMillis());
                orgInCarDay.setUpdateType(2);// TODO: 2023/10/18 写死UpdateType = 2(店面管理)
                orgInCarDay.setOrgId(orgId);
                orgInCarDayMapper.insert(orgInCarDay);
            }else {
                //3.2 数据库中有该条数据，则执行更新操作
                this.lambdaUpdate()
                        .set(OrgInCarDay::getCarCount, orgInCarDayDTO.getCarCount())
                        .eq(OrgInCarDay::getOrgId, orgId)
                        .eq(OrgInCarDay::getDayIndex, orgInCarDayDTO.getDayIndex())
                        .update();
            }
        });
//        orgInCarDayMapper.insertBatch(orgInCarDayList);
    }
}
