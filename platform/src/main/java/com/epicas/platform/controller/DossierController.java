package com.epicas.platform.controller;

import com.epicas.platform.domain.dto.AddDossierDTO;
import com.epicas.platform.domain.dto.QueryDossierDTO;
import com.epicas.platform.domain.vo.CarDossierVO;
import com.epicas.platform.domain.vo.DossierListVO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.result.Result;
import com.epicas.platform.service.DossierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 档案管理 前端控制器
 * @author liuyang
 * @date 2023年10月08日 17:59
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("dossier")
@Api(tags = "档案列表相关接口")
public class DossierController {

    private final DossierService dossierService;

    @ApiOperation("获取档案id和部位id")
    @PostMapping("/findDossierList")
    @EpicasLog(value = "获取档案id和部位id", paramIndex = {0})
    public Result<DossierListVO> findDossierList(@RequestBody @Validated QueryDossierDTO queryDossierDTO){
        DossierListVO dossierListVO = dossierService.findDossierList(queryDossierDTO);
        return Result.ok(dossierListVO);
    }

    @ApiOperation("车辆进店创建档案")
    @PostMapping("/saveDossier")
    @EpicasLog(value = "车辆进店创建档案", paramIndex = {0})
    public Result saveDossier(@RequestBody @Validated AddDossierDTO addDossierDTO){
        dossierService.addDossier(addDossierDTO);
        return Result.ok();
    }

    @ApiOperation("根据站点id和车牌号查询档案列表")
    @PostMapping("/findDossierListByCarNumber")
    @EpicasLog(value = "根据站点id和车牌号查询档案列表", paramIndex = {0})
    public Result<List<CarDossierVO>> findDossierListByCarNumber(@RequestBody @Validated QueryDossierDTO queryDossierDTO){
        return Result.ok(dossierService.findDossierListByOrgIdAndCarNumber(queryDossierDTO));
    }

    @ApiOperation("根据站点id和检测日期查询档案列表")
    @PostMapping("/findDossierListByCheckDay")
    @EpicasLog(value = "根据站点id和检测日期查询档案列表", paramIndex = {0})
    public Result<List<CarDossierVO>> findDossierListByCheckDay(@RequestBody @Validated QueryDossierDTO queryDossierDTO){
        return Result.ok(dossierService.findDossierListByOrgIdAndCheckDay(queryDossierDTO));
    }
}
