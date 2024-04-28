package com.epicas.platform.controller;

import com.epicas.platform.domain.dto.AddOrgCarWhiteListDTO;
import com.epicas.platform.domain.dto.UpdateStaffCarWhiteListDTO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.result.Result;
import com.epicas.platform.service.StaffCarWhiteListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 员工车辆白名单 前端控制器
 * @author liuyang
 * @date 2023年10月10日 15:45
 */
@RestController
@Api(tags = "员工车辆白名单相关接口")
@RequestMapping("staffCarWhiteList")
@RequiredArgsConstructor
public class StaffCarWhiteListController {

    private final StaffCarWhiteListService staffCarWhiteListService;

    @ApiOperation("添加员工车辆白名单")
    @PostMapping("/addStaffCarWhiteList")
    @EpicasLog(value = "添加员工车辆白名单", paramIndex = {0})
    public Result addStaffCarWhiteList(@RequestBody @Validated AddOrgCarWhiteListDTO addOrgCarWhiteListDTO) {
        staffCarWhiteListService.addOrgCarWhiteList(addOrgCarWhiteListDTO);
        return Result.ok();
    }

    @PostMapping("/updateStaffCarWhiteList")
    @ApiOperation("修改员工车辆白名单是否移除的状态")
    @EpicasLog(value = "根据carId移除员工车俩白名单", paramIndex = {0})
    public Result updateStaffCarWhiteList(@RequestBody @Validated UpdateStaffCarWhiteListDTO updateStaffCarWhiteListDTO) {
        staffCarWhiteListService.updateStaffCarWhiteList(updateStaffCarWhiteListDTO);
        return Result.ok();
    }
}
