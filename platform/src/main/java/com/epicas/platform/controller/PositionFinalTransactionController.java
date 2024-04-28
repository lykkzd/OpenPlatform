package com.epicas.platform.controller;

import com.epicas.platform.domain.dto.PositionFinalTransactionDTO;
import com.epicas.platform.log.EpicasLog;
import com.epicas.platform.result.Result;
import com.epicas.platform.service.PositionFinalTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆部位最后成交情况 前端控制器
 * @author liuyang
 * @date 2023年10月11日 10:35
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("finalTransaction")
@Api(tags = "车辆部位最后成交情况相关接口")
public class PositionFinalTransactionController {

    private final PositionFinalTransactionService positionFinalTransactionService;

    @ApiOperation("记录车辆部位最后成交情况")
    @PostMapping("/recordFinalTransaction")
    @EpicasLog(value = "记录车辆部位最后成交情况", paramIndex = {0})
    public Result recordFinalTransaction(@RequestBody @Validated PositionFinalTransactionDTO dto) {
        positionFinalTransactionService.recordFinalTransaction(dto);
        return Result.ok();
    }

    @PostMapping("/removeFinalTransaction")
    @ApiOperation("根据carId移除车辆部位最后成交情况")
    @EpicasLog(value = "根据carId移除对应的车辆部位最后成交情况", paramIndex = {0})
    public Result removeFinalTransaction(@RequestBody @Validated PositionFinalTransactionDTO dto) {
        positionFinalTransactionService.removePositionFinalTransaction(dto);
        return Result.ok();
    }
}
