package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.dto.AddDossierDTO;
import com.epicas.platform.domain.dto.QueryDossierDTO;
import com.epicas.platform.domain.en.IsEffective;
import com.epicas.platform.domain.po.CustomerCarList;
import com.epicas.platform.domain.po.Dossier;
import com.epicas.platform.domain.vo.*;
import com.epicas.platform.exception.CarNumberNotExistException;
import com.epicas.platform.exception.DbException;
import com.epicas.platform.exception.EmptyEffectivePlanListException;
import com.epicas.platform.exception.OrgIdNotExistException;
import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.mapper.DossierMapper;
import com.epicas.platform.service.CustomerCarListService;
import com.epicas.platform.service.DossierPositionService;
import com.epicas.platform.service.DossierService;
import com.epicas.platform.service.OrgPlanListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 对应档案逻辑的service层的实现类
 * @author liuyang
 * @date 2023年10月08日 18:04
 */
@Service
public class DossierServiceImpl extends ServiceImpl<DossierMapper, Dossier> implements DossierService {

    @Autowired
    private CustomerCarListService customerCarListService;
    @Autowired
    private DossierMapper dossierMapper;
    @Autowired
    private DossierPositionService dossierPositionService;
    @Autowired
    private OrgPlanListService orgPlanListService;

    /**
     * 获取档案id以及部位id
     * @param queryDossierDTO
     * @return
     */
    @Transactional
    @Override
    public DossierListVO findDossierList(QueryDossierDTO queryDossierDTO) {
        //1. 本地线程拿到orgId
        Long orgId = OrgIdHolder.getOrgId();
        //1.1 拿不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }
        //2. 判断carNumber是否为空
        LambdaQueryWrapper<Dossier> queryWrapper = new LambdaQueryWrapper();
        //3. 初始化集合
        List<Dossier> dossierList;
        if (StringUtils.isEmpty(queryDossierDTO.getCarNumber())) {
            //4. 如果为空，则根据checkDay和orgId查询档案列表
            queryWrapper.eq(Dossier::getOrgId, orgId)
                    .eq(Dossier::getCheckDay, queryDossierDTO.getCheckDay())
                    .select(Dossier::getDossierId);
        } else {
            //5. 如果不为空，则根据carNumber到customer_car_list表中查到对应的carId
            CustomerCarListVO customerCarListVO = customerCarListService.getCustomerCarListByCarNumber(queryDossierDTO.getCarNumber());
            if (customerCarListVO == null) {
                throw new CarNumberNotExistException();
            }
            //6. 根据carId、orgId、checkDay查询档案列表
            queryWrapper.eq(Dossier::getOrgId, orgId)
                    .eq(Dossier::getCheckDay, queryDossierDTO.getCheckDay())
                    .eq(Dossier::getCarId, customerCarListVO.getId())
                    .select(Dossier::getDossierId);
        }
        //7. 查询出档案列表
        dossierList = dossierMapper.selectList(queryWrapper);
        //8. 将查询出来的信息封装到DossierListVO中
        DossierListVO dossierListVO = new DossierListVO();
        dossierListVO.setCheckDay(queryDossierDTO.getCheckDay());
        dossierListVO.setCarNumber(queryDossierDTO.getCarNumber());

        Map<Long, List<Long>> dossierMap = new HashMap<>();
        //9. 根据每一个档案，查询出该档案的检查项列表
        if (!CollectionUtils.isEmpty(dossierList)) {
            for (Dossier dossier : dossierList) {
                List<Long> dossierPositionIdList = dossierPositionService.findDossierPositionList(dossier.getDossierId()).stream()
                        .map(DossierPositionVO::getPositionId).collect(Collectors.toList());
                dossierMap.put(dossier.getDossierId(), dossierPositionIdList);
            }
        }
        dossierListVO.setDossierMap(dossierMap);

        return dossierListVO;
    }

    /**
     * 车辆进店创建档案
     * @param addDossierDTO
     */
    @Transactional
    @Override
    public void addDossier(AddDossierDTO addDossierDTO) {
        //1. 本地线程拿到orgId
        Long orgId = OrgIdHolder.getOrgId();
        //1.1 拿不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }
        //2. 查询当前orgId下的有效方案id
        List<Long> planIdList = orgPlanListService.findPlanIdList(orgId, IsEffective.EFFECTIVE.getValue());
        if (CollectionUtils.isEmpty(planIdList)) {
            throw new EmptyEffectivePlanListException();
        }
        //3. 获取最后一个方案id
        Long planId = planIdList.get(planIdList.size() - 1);
        //4. 根据carNumber查询carId
        CustomerCarListVO customerCarListVO = customerCarListService.getCustomerCarListByCarNumber(addDossierDTO.getCarNumber());

        CustomerCarList customerCarList = new CustomerCarList();
        if (customerCarListVO == null) {
            //5. 如果不存在，则新增一条car记录
            customerCarList.setCarNumber(addDossierDTO.getCarNumber());
            customerCarList.setFrameNumber(addDossierDTO.getFrameNumber());
            customerCarList.setInitOrgId(orgId);
            // TODO: 2023/10/11 汽车表插入数据时写死字段
            customerCarList.setFuelTypeId(1L);// 汽油类型
            customerCarList.setModelId(562L);// 车型Id

            customerCarListService.save(customerCarList);
        }else {
            //6. 如果存在，则更新car记录
            if (!StringUtils.isEmpty(addDossierDTO.getFrameNumber())) {
                customerCarListVO.setFrameNumber(addDossierDTO.getFrameNumber());

                BeanUtils.copyProperties(customerCarListVO, customerCarList);

                customerCarListService.updateById(customerCarList);
            }
        }
        //7. 新增一条档案记录
        Dossier dossier = new Dossier();
        dossier.setOrgId(orgId);
        dossier.setCarId(customerCarList.getId());
        dossier.setPlanId(planId);
        dossier.setCheckDay(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dossier.setSaId(0L);// TODO: 2023/10/10 创建档案部分数据写死。
        dossier.setCarNumberImageId(0L);
        dossier.setMile(0L);
        dossier.setInputTime(System.currentTimeMillis());
        dossierMapper.insert(dossier);
    }

    /**
     * 根据站点id和车牌号查询档案列表
     * @param queryDossierDTO
     * @return
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<CarDossierVO> findDossierListByOrgIdAndCarNumber(QueryDossierDTO queryDossierDTO) {
        //1. 本地线程拿到orgId
        Long orgId = OrgIdHolder.getOrgId();
        //1.1 拿不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }
        //2. 根据车牌号查询车辆信息
        CustomerCarListVO customerCarListVO = customerCarListService.getCustomerCarListByCarNumber(queryDossierDTO.getCarNumber());
        if (customerCarListVO == null) {
            throw new CarNumberNotExistException();
        }
        //3. 根据orgId、carId查询档案列表
        LambdaQueryWrapper<Dossier> queryWrapper = new LambdaQueryWrapper<Dossier>();
        queryWrapper.eq(Dossier::getOrgId, orgId)
                .eq(Dossier::getCarId, customerCarListVO.getId())
                .select(Dossier::getDossierId, Dossier::getCheckDay);
        //4. 返回[carNumber，dossierId，checkDay]列表
        return dossierMapper.selectList(queryWrapper)
                .stream()
                .map(dossier -> {
                    CarDossierVO carDossierVO = new CarDossierVO();
                    BeanUtils.copyProperties(dossier, carDossierVO);

                    carDossierVO.setCarNumber(queryDossierDTO.getCarNumber());
                    return carDossierVO;
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据站点id和检测日期查询档案列表
     * @param queryDossierDTO
     * @return
     */
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    @Override
    public List<CarDossierVO> findDossierListByOrgIdAndCheckDay(QueryDossierDTO queryDossierDTO) {
        //1. 本地线程拿到orgId
        Long orgId = OrgIdHolder.getOrgId();
        //1.1 拿不到则抛异常
        if (orgId == null) {
            throw new OrgIdNotExistException();
        }
        //2.根据orgId、checkDay查询档案列表
        LambdaQueryWrapper<Dossier> queryWrapper = new LambdaQueryWrapper<Dossier>();
        queryWrapper.eq(Dossier::getOrgId, orgId)
                .eq(Dossier::getCheckDay, queryDossierDTO.getCheckDay())
                .select(Dossier::getDossierId,Dossier::getCarId,Dossier::getCheckDay);
        List<Dossier> dossierList = dossierMapper.selectList(queryWrapper);
        //3. 根据carId查询车辆信息
        List<Long> carIdList = dossierList.stream()
                .map(Dossier::getCarId)
                .collect(Collectors.toList());
        List<CustomerCarListVO> customerCarListVOList = customerCarListService.findCustomerCarListVOListByCarIdList(carIdList);
        //4. 将查询出来的信息封装到CarDossierVO中
        List<CarDossierVO> carDossierVOList = dossierList.stream().map(dossier -> {
            //4.1 创建CarDossierVO
            CarDossierVO carDossierVO = new CarDossierVO();
            //4.2 赋值dossier中字段
            carDossierVO.setDossierId(dossier.getDossierId());
            carDossierVO.setCheckDay(dossier.getCheckDay());
            for (CustomerCarListVO customerCarListVO : customerCarListVOList) {
                //4.3 赋值车牌号
                carDossierVO.setCarNumber(customerCarListVO.getCarNumber());
            }
            return carDossierVO;
        }).collect(Collectors.toList());
        return carDossierVOList;
    }

    @Override
    public List<DossierVO> findDossierVOList(List<Integer> dossierIdList) {
        LambdaQueryWrapper<Dossier> queryWrapper = new LambdaQueryWrapper<Dossier>();
        queryWrapper.in(Dossier::getDossierId, dossierIdList)
                .select(Dossier::getDossierId, Dossier::getOrgId);
        return dossierMapper.selectList(queryWrapper)
                .stream()
                .map(dossier -> {
                    DossierVO dossierVO = new DossierVO();
                    BeanUtils.copyProperties(dossier, dossierVO);
                    return dossierVO;
                })
                .collect(Collectors.toList());
    }
}

