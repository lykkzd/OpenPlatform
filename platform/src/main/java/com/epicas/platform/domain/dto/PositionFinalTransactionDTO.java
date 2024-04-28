package com.epicas.platform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO用于接收前端参数
 * @author liuyang
 * @date 2023年10月11日 10:51
 */
@Data
@ApiModel(description = "车辆部位最后成交情况")
public class PositionFinalTransactionDTO implements Serializable {

    @ApiModelProperty(value = "部位id", required = true)
    @NotNull(message = "部位id不能为空")
    private Long positionId;

    @ApiModelProperty(value = "车牌号", required = true)
    @NotNull(message = "车牌号不能为空")
    private String carNumber;

    @ApiModelProperty(value = "成交时间", required = true)
    @NotNull(message = "成交时间不能为空")
    private Long transactionTime;
}
