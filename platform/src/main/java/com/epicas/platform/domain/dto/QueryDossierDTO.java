package com.epicas.platform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO用于接收前端参数
 * @author liuyang
 * @date 2023年10月08日 17:22
 */

@Data
@ApiModel(description = "查询档案信息")
public class QueryDossierDTO implements Serializable {

    @ApiModelProperty("车牌号(没有车牌号可以不传)")
    private String carNumber;

    @ApiModelProperty(value = "检测日期", example = "2021-05-15", required = true)
    @NotNull(message = "检测日期不能为空")
    private String checkDay;
}
