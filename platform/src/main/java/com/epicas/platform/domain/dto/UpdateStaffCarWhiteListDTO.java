package com.epicas.platform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO用于接收前端参数
 * @author liuyang
 * @date 2023年10月10日 16:30
 */
@Data
@ApiModel(description = "修改员工车辆白名单")
public class UpdateStaffCarWhiteListDTO implements Serializable {

    @ApiModelProperty(value = "车牌号", required = true)
    @NotNull(message = "车牌号不能为空")
    private String carNumber;

    @ApiModelProperty(value = "是否移除：0-未移除 1-移除", required = true)
    private Integer isDeleted;
}
