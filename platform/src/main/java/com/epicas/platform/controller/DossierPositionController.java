package com.epicas.platform.controller;

import com.epicas.platform.domain.dto.ChangePositionRegisterDTO;
import com.epicas.platform.domain.vo.DossierPositionVO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.result.Result;
import com.epicas.platform.service.DossierPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 档案部位 前端控制器
 * @author liuyang
 * @date 2023年10月11日 15:18
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "档案部位检测相关接口")
@RequestMapping("dossierPosition")
public class DossierPositionController {

    private final DossierPositionService dossierPositionService;

    @ApiOperation("Excel进行部位成交登记")
    @PostMapping("/positionDealRegisterWithExcel")
    @EpicasLog(value = "Excel进行部位成交登记")
    public Result positionDealRegisterWithExcel(@RequestPart("file") MultipartFile file) throws Exception {
        return Result.ok(dossierPositionService.positionDealRegisterWithExcel(file));
    }

    @ApiOperation("DTO进行部位成交登记")
    @PostMapping("/positionDealRegisterWithDTO")
    @EpicasLog(value = "DTO进行部位成交登记")
    public Result positionDealRegisterWithDTO(@RequestBody List<ChangePositionRegisterDTO> changePositionRegisterDTOList) {
        return Result.ok(dossierPositionService.positionDealRegisterWithDTO(changePositionRegisterDTOList));
    }

    @GetMapping("/{dossierId}")
    @ApiOperation("根据档案Id查询档案部位表中的数据")
    @EpicasLog(value = "根据档案Id查询档案部位表中的数据", paramIndex = {0})
    public Result<List<DossierPositionVO>> findDossierPositionList(
            @NotNull(message = "档案id不能为空") @PathVariable("dossierId") Long dossierId
    ) {
        return Result.ok(dossierPositionService.findDossierPositionList(dossierId));
    }
}
