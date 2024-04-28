package com.epicas.platform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.epicas.platform.constants.PartsPriceConstant;
import com.epicas.platform.domain.po.PartsPrice;
import com.epicas.platform.domain.dto.PartsPriceDTO;
import com.epicas.platform.exception.DTONotNullException;
import com.epicas.platform.exception.ExceedImportCountException;
import com.epicas.platform.exception.ExcelParseException;
import com.epicas.platform.exception.ShowTypeException;
import com.epicas.platform.holder.OrgIdHolder;
import com.epicas.platform.mapper.PartsPriceMapper;
import com.epicas.platform.service.PartsPriceService;
import com.epicas.platform.utils.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PartsPriceServiceImpl extends ServiceImpl<PartsPriceMapper, PartsPrice> implements PartsPriceService {

    private final PartsPriceMapper partsPriceMapper;

    /**
     * 批量添加配件价格
     * @param file
     */
    @Transactional
    @Override
    public void addPartsPriceListWithExcel(MultipartFile file) {
        //0. 根据orgId查询该站点的所有配件价格
        List<PartsPrice> currentOrgPartsPriceList = this.lambdaQuery().eq(PartsPrice::getOrgId, OrgIdHolder.getOrgId()).list();
        try {
            //1. 解析Excel表格传入过来的数据，转换成DTO列表
            List<PartsPriceDTO> partsPriceDTOList = ExcelUtils.readMultipartFile(file, PartsPriceDTO.class);
            //2. 限制导入数量，不允许超过1000
            if (partsPriceDTOList.size() > 1000) {
                throw new ExceedImportCountException();
            }
            //3. 处理当前批次的数据
            batchAddPartsPrice(partsPriceDTOList, currentOrgPartsPriceList);
        } catch (Exception e) {
            throw new ExcelParseException();
        }
    }

    @Override
    public void addPartsPriceListWithDTO(List<PartsPriceDTO> partsPriceDTOList) {
        //1. 参数校验
        partsPriceDTOList.stream().forEach(partsPriceDTO -> {
            if (partsPriceDTO.getPositionId() == null
                    || partsPriceDTO.getPartsName() == null
                    || partsPriceDTO.getPartsNo() == null
                    || partsPriceDTO.getPrice() == null) {
                throw new DTONotNullException();
            }
            if (partsPriceDTO.getShowType() != 0 && partsPriceDTO.getShowType() != 1) {
                throw new ShowTypeException();
            }
        });
        //2. 根据orgId查询该站点的所有配件价格
        List<PartsPrice> currentOrgPartsPriceList = this.lambdaQuery().eq(PartsPrice::getOrgId, OrgIdHolder.getOrgId()).list();
        //3. 处理当前批次的数据
        batchAddPartsPrice(partsPriceDTOList, currentOrgPartsPriceList);
    }

    private void batchAddPartsPrice(List<PartsPriceDTO> partsPriceDTOList, List<PartsPrice> currentOrgPartsPriceList) {
        //1. 处理当前批次的数据
        for (PartsPriceDTO partsPriceDTO : partsPriceDTOList) {
            String partsPriceVOMd5 = DigestUtils.md5DigestAsHex((partsPriceDTO.getShowType() + PartsPriceConstant.MD5_DIGEST_SEPARATOR
                    + partsPriceDTO.getPositionId() + PartsPriceConstant.MD5_DIGEST_SEPARATOR
                    + partsPriceDTO.getPartsName() + PartsPriceConstant.MD5_DIGEST_SEPARATOR
                    + partsPriceDTO.getPartsNo()).getBytes(PartsPriceConstant.STRING_TO_BYTES_CTARSET));
            //2. 将配件单价由元改成分
            Double price = partsPriceDTO.getPrice();
            partsPriceDTO.setPrice(price * 100);

            //3. 默认为未找到匹配项
            boolean foundMatch = false;

            for (PartsPrice partsPrice : currentOrgPartsPriceList) {
                String partsPriceDOMd5 = DigestUtils.md5DigestAsHex((partsPrice.getShowType() + PartsPriceConstant.MD5_DIGEST_SEPARATOR
                        + partsPrice.getPositionId() + PartsPriceConstant.MD5_DIGEST_SEPARATOR
                        + partsPrice.getPartsName() + PartsPriceConstant.MD5_DIGEST_SEPARATOR
                        + partsPrice.getPartsNo()).getBytes(PartsPriceConstant.STRING_TO_BYTES_CTARSET));
                if (partsPriceVOMd5.equals(partsPriceDOMd5)) {
                    //4. 数据库中有该数据，更新数据库即可
                    BeanUtils.copyProperties(partsPriceDTO, partsPrice);
                    partsPrice.setOrgId(OrgIdHolder.getOrgId());
                    partsPriceMapper.updateById(partsPrice);
                    foundMatch = true;
                    break; // 找到匹配项，跳出循环
                }
            }

            if (!foundMatch) {
                //5. 未找到匹配项，执行插入
                PartsPrice partsPrice = new PartsPrice();
                BeanUtils.copyProperties(partsPriceDTO, partsPrice);
                partsPrice.setOrgId(OrgIdHolder.getOrgId());
                partsPriceMapper.insert(partsPrice);
            }
        }
    }
}

