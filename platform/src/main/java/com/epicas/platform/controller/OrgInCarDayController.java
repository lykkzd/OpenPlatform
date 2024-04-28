package com.epicas.platform.controller;

import com.epicas.platform.domain.dto.OrgInCarDayDTO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.result.Result;
import com.epicas.platform.service.OrgInCarDayService;
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
 * 进店车辆详情 前端控制器
 * @author liuyang
 * @date 2023年10月08日 16:49
 */

@RestController
@RequiredArgsConstructor
@Api(tags = "进店车辆相关接口")
@RequestMapping("orgInCarDay")
public class OrgInCarDayController {

    private final OrgInCarDayService orgInCarDayService;

    @ApiOperation("保存进店台次和保养基数")
    @PostMapping("/saveOrgInCarDayList")
    @EpicasLog(value = "保存进店台次和保养基数", paramIndex = {0})
    public Result saveOrgInCarDayList(@RequestBody @Validated List<OrgInCarDayDTO> orgInCarDayDTOList){
        orgInCarDayService.saveOrgInCarDayList(orgInCarDayDTOList);
        return Result.ok();
    }
}
