package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.dto.ChangePositionRegisterDTO;
import com.epicas.platform.domain.po.DossierPosition;
import com.epicas.platform.domain.vo.ChangePositionRegisterVO;
import com.epicas.platform.domain.vo.DossierPositionVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author liuyang
 * @date 2023年10月09日 16:48
 */
public interface DossierPositionService extends IService<DossierPosition> {

    /**
     * Excel进行部位成交登记
     * @param file
     * @return
     */
    ChangePositionRegisterVO positionDealRegisterWithExcel(MultipartFile file) throws Exception;

    /**
     * DTO进行部位成交登记
     * @param changePositionRegisterDTOList
     * @return
     */
    ChangePositionRegisterVO positionDealRegisterWithDTO(List<ChangePositionRegisterDTO> changePositionRegisterDTOList);

    /**
     * 根据档案id查询档案部位表中数据
     * @param dossierId
     * @return
     */
    List<DossierPositionVO> findDossierPositionList(Long dossierId);



}
