package com.epicas.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.domain.dto.ChangePositionRegisterDTO;
import com.epicas.platform.domain.en.DealOrNot;
import com.epicas.platform.domain.po.DossierPosition;
import com.epicas.platform.domain.vo.ChangePositionRegisterVO;
import com.epicas.platform.domain.vo.DossierPositionVO;
import com.epicas.platform.domain.vo.DossierVO;
import com.epicas.platform.exception.*;
import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.mapper.DossierPositionMapper;
import com.epicas.platform.service.DossierPositionService;
import com.epicas.platform.service.DossierService;
import com.epicas.platform.utils.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * 部位检测报告的service层的实现类
 *
 * @author liuyang
 * @date 2023年10月09日 16:51
 */
@Service
public class DossierPositionServiceImpl extends ServiceImpl<DossierPositionMapper, DossierPosition> implements DossierPositionService {

    @Autowired
    private DossierPositionMapper dossierPositionMapper;
    @Autowired
    private DossierService dossierService;
    
    /**
     * Excel进行部位成交登记
     *
     * @param file
     * @return
     */
    @Transactional
    @Override
    public ChangePositionRegisterVO positionDealRegisterWithExcel(MultipartFile file) {
        //0. 创建一个计数器，用来记录成功的行数
        AtomicInteger successCount = new AtomicInteger();
        //1. 初始化数据
        //1.1 创建一个Map集合，用来存储不匹配的行号和原因
        Map<Integer, String> failMap = new HashMap<>();
        //1.2 创建一个List集合，用来存储所有类型校验成功的dosserId
        List<Integer> dossierIdList = new ArrayList<>();
        //1.3 初始化DTO对象列表，用来接收Excel批量导入过来的数据
        List<ChangePositionRegisterDTO> changePositionRegisterDTOList;
        try {
            //2. 解析Excel表格传入过来的数据，转换成DTO对象列表
            changePositionRegisterDTOList = ExcelUtils.readMultipartFile(file, ChangePositionRegisterDTO.class);
        } catch (Exception e) {
            throw new ExcelParseException();
        }
        //3. 限制导入数量，不允许超过1000
        if (changePositionRegisterDTOList.size() > 1000) {
            throw new ExceedImportCountException();
        }
        //4. 遍历 changePositionRegisterDTOList
        changePositionRegisterDTOList = changePositionRegisterDTOList.stream()
                .filter(changePositionRegisterDTO -> {
                    if (!changePositionRegisterDTO.getRowTips().equals("")) {
                        //4.1 将导入失败的数据存入 failMap 集合中
                        failMap.put(changePositionRegisterDTO.getRowNum(), changePositionRegisterDTO.getRowTips());
                        return false; // 返回 false 表示不包含在结果中
                    } else {
                        //4.2 将成功导入的 dossierId 存入 dossierIdList 集合中
                        Integer dossierId = changePositionRegisterDTO.getDossierId();
                        dossierIdList.add(dossierId);
                        return true; // 返回 true 表示包含在结果中
                    }
                }).collect(Collectors.toList());
        //5. 判断是否有成功导入的数据
        if (dossierIdList == null || dossierIdList.size() == 0) {
            //5.1 如果没有直接封装VO返回：包含成功的行数（此时没有成功的数量）和不匹配的行号和原因
            ChangePositionRegisterVO changePositionRegisterVO = new ChangePositionRegisterVO();
            changePositionRegisterVO.setFailMap(failMap);
            changePositionRegisterVO.setSuccessCount(0);
            return changePositionRegisterVO;
        }
        //6. 进行部位成交登记
        return positionDealRegister(changePositionRegisterDTOList, failMap, dossierIdList, successCount);
    }

    /**
     * DTO进行部位成交登记
     * @param changePositionRegisterDTOList
     * @return
     */
    @Override
    public ChangePositionRegisterVO positionDealRegisterWithDTO(List<ChangePositionRegisterDTO> changePositionRegisterDTOList) {
        //0. 创建一个计数器，用来记录成功的行数
        AtomicInteger successCount = new AtomicInteger();
        //1. 初始化数据
        //1.1 创建一个Map集合，用来存储不匹配的行号和原因
        Map<Integer, String> failMap = new HashMap<>();
        //1.2 创建一个List集合，用来存储所有类型校验成功的dosserId
        List<Integer> dossierIdList = new ArrayList<>();
        //2. 遍历 changePositionRegisterDTOList
        changePositionRegisterDTOList.stream().forEach(changePositionRegisterDTO -> {
            //2.1 参数校验
            if (changePositionRegisterDTO.getRowNum() == null
                    || changePositionRegisterDTO.getDossierId() == null
                    || changePositionRegisterDTO.getPositionId() == null) {
                throw new DTONotNullException();
            }
            if (changePositionRegisterDTO.getDealOrNot() != DealOrNot.NOT_DEAL.getCode()
                    || changePositionRegisterDTO.getDealOrNot() != DealOrNot.DEAL.getCode()) {
                throw new DealOrNotTypeException();
            }
            //2.2 将 dossierId 存入 dossierIdList
            Integer dossierId = changePositionRegisterDTO.getDossierId();
            dossierIdList.add(dossierId);
        });
        //3. 进行部位成交登记
        return positionDealRegister(changePositionRegisterDTOList, failMap, dossierIdList, successCount);
    }

    private ChangePositionRegisterVO positionDealRegister(List<ChangePositionRegisterDTO> changePositionRegisterDTOList, Map<Integer, String> failMap, List<Integer> dossierIdList, AtomicInteger successCount) {
        //1. 根据 dossierIdList 进行批量查询
        List<DossierVO> dossierVOList = dossierService.findDossierVOList(dossierIdList);
        //2. 根据 dossierVOList 进行校验
        //2.1 查询orgId是否匹配请求头中的orgId
        Long orgId = OrgIdHolder.getOrgId();
        //2.2 查询出不匹配的数据
        List<DossierVO> unMatchDossierVOList = dossierVOList.stream()
                .filter(dossierVO -> !dossierVO.getOrgId().equals(orgId))
                .collect(Collectors.toList());
        //2.3 把不匹配的数据的行号和原因存储到集合中
        changePositionRegisterDTOList.stream()
                .filter(changePositionRegisterDTO -> unMatchDossierVOList.stream()
                        .anyMatch(dossierVO -> dossierVO.getDossierId().equals(changePositionRegisterDTO.getDossierId())))
                .forEach(changePositionRegisterDTO -> failMap.put(changePositionRegisterDTO.getRowNum(), "档案id和站点id不匹配"));

        //2.4 过滤掉不匹配的数据
        changePositionRegisterDTOList = changePositionRegisterDTOList.stream()
                .filter(changePositionRegisterDTO -> !failMap.containsKey(changePositionRegisterDTO.getRowNum()))
                .collect(Collectors.toList());
        //3. 根据dossierIdList查询出所有的dossierPositionList
        List<DossierPosition> dossierPositionList = this.lambdaQuery()
                .in(DossierPosition::getDossierId, dossierIdList)
                .select(DossierPosition::getDossierId, DossierPosition::getPositionId)
                .list();
        //4. 根据dossierPositionList进行校验
        //4.1 把不匹配的数据的行号和原因存储到集合中
        changePositionRegisterDTOList.stream()
                .filter(changePositionRegisterDTO -> dossierPositionList.stream()
                        .noneMatch(dossierPosition -> dossierPosition.getDossierId().equals(changePositionRegisterDTO.getDossierId())
                                && dossierPosition.getPositionId().equals(changePositionRegisterDTO.getPositionId())))
                .forEach(changePositionRegisterDTO -> failMap.put(changePositionRegisterDTO.getRowNum(), "档案id和部位id不匹配"));
        //4.2 过滤掉不匹配的数据
        changePositionRegisterDTOList = changePositionRegisterDTOList.stream()
                .filter(changePositionRegisterDTO -> !failMap.containsKey(changePositionRegisterDTO.getRowNum()))
                .collect(Collectors.toList());
        //5. 根据changePositionRegisterDTOList进行批量更新
        changePositionRegisterDTOList.stream().forEach(ChangePositionRegisterDTO -> {
            lambdaUpdate()
                    .set(ChangePositionRegisterDTO.getDealOrNot() == DealOrNot.DEAL.getCode(), DossierPosition::getUsFeedback, 800)
                    .set(ChangePositionRegisterDTO.getDealOrNot() == DealOrNot.NOT_DEAL.getCode(), DossierPosition::getUsFeedback, 400)
                    .eq(DossierPosition::getDossierId, ChangePositionRegisterDTO.getDossierId())
                    .eq(DossierPosition::getPositionId, ChangePositionRegisterDTO.getPositionId())
                    .update();
            successCount.getAndIncrement();
        });
        //6. 封装VO返回: 包含成功的行数和不匹配的行号和原因
        ChangePositionRegisterVO changePositionRegisterVO = new ChangePositionRegisterVO();
        changePositionRegisterVO.setSuccessCount(successCount.get());
        changePositionRegisterVO.setFailMap(failMap);
        return changePositionRegisterVO;
    }

    /**
     * 根据档案id查询档案部位表中的数据
     * @param dossierId
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<DossierPositionVO> findDossierPositionList(Long dossierId) {
        LambdaQueryWrapper<DossierPosition> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DossierPosition::getDossierId, dossierId)
                .select(DossierPosition::getPositionId, DossierPosition::getDiagnosis, DossierPosition::getPositionCheckTag);
        List<DossierPosition> dossierPositionList = dossierPositionMapper.selectList(queryWrapper);
        return dossierPositionList.stream()
                .map(dossierPosition -> {
                    //将po转换为vo并返回
                    DossierPositionVO dossierPositionVO = new DossierPositionVO();
                    BeanUtils.copyProperties(dossierPosition, dossierPositionVO);
                    return dossierPositionVO;
                }).collect(Collectors.toList());
    }
}
