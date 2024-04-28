package com.epicas.platform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO用于接收前端参数
 * @author liuyang
 * @date 2023年10月07日 17:26
 */
@Data
@ApiModel(description = "保存进店台次和保养基数")
public class OrgInCarDayDTO implements Serializable {

    @ApiModelProperty(value = "时间戳", required = true)
    @NotNull(message = "时间戳不能为空")
    private String dayIndex;

    @ApiModelProperty(value = "汽车数量", required = true)
    @NotNull(message = "汽车数量不能为空")
    private Integer carCount;
}
