package com.epicas.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.epicas.platform.domain.po.PartsPrice;
import com.epicas.platform.domain.dto.PartsPriceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author liuyang
 * @date 2023年09月28日 17:14
 */
public interface PartsPriceService extends IService<PartsPrice> {

    /**
     * 批量添加配件价格
     * @param file
     */
    void addPartsPriceListWithExcel(MultipartFile file);

    void addPartsPriceListWithDTO(List<PartsPriceDTO> partsPriceDTOList);
}
