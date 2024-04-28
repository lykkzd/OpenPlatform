package com.epicas.platform.controller;

import com.epicas.platform.domain.dto.PartsPriceDTO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.result.Result;
import com.epicas.platform.service.PartsPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 配件价格明细 前端控制器
 * @author liuyang
 * @date 2023年10月08日 15:30
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "配件价格相关接口")
@RequestMapping("partsPrice")
public class PartsPriceController {

    private final PartsPriceService partsPriceService;

    @ApiOperation("Excel批量导入零配件数据")
    @EpicasLog(value = "Excel批量导入零配件数据")
    @PostMapping("/addPartsPriceListWithExcel")
    public Result addPartsPriceListWithExcel(@RequestPart("file") MultipartFile file) {
        partsPriceService.addPartsPriceListWithExcel(file);
        return Result.ok();
    }

    @ApiOperation("DTO批量导入零配件数据")
    @EpicasLog(value = "DTO批量导入零配件数据")
    @PostMapping("/addPartsPriceListWithDTO")
    public Result addPartsPriceListWithDTO(@RequestBody List<PartsPriceDTO> partsPriceDTOList) {
        partsPriceService.addPartsPriceListWithDTO(partsPriceDTOList);
        return Result.ok();
    }
}
