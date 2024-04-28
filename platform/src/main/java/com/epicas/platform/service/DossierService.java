package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.dto.AddDossierDTO;
import com.epicas.platform.domain.dto.QueryDossierDTO;
import com.epicas.platform.domain.po.Dossier;
import com.epicas.platform.domain.vo.CarDossierVO;
import com.epicas.platform.domain.vo.DossierListVO;
import com.epicas.platform.domain.vo.DossierVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author liuyang
 * @date 2023年10月08日 18:03
 */
public interface DossierService extends IService<Dossier> {
    /**
     * 获取档案id以及部位id
     * @param queryDossierDTO
     * @return
     */
    DossierListVO findDossierList(QueryDossierDTO queryDossierDTO);

    /**
     * 车辆进店创建档案
     * @param addDossierDTO
     */
    void addDossier(AddDossierDTO addDossierDTO);

    /**
     * 根据站点id和车牌号查询档案列表
     * @param queryDossierDTO
     * @return
     */
    List<CarDossierVO> findDossierListByOrgIdAndCarNumber(QueryDossierDTO queryDossierDTO);

    /**
     * 根据机构id和检测日期查询档案列表
     * @param queryDossierDTO
     * @return
     */
    List<CarDossierVO> findDossierListByOrgIdAndCheckDay(QueryDossierDTO queryDossierDTO);

    /**
     * 批量查询档案信息
     * @param dossierIdList
     * @return
     */
    List<DossierVO> findDossierVOList(List<Integer> dossierIdList);
}
