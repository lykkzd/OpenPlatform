package com.epicas.platform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyang
 * @date 2023年10月11日 14:01
 */
@Data
@ApiModel(description = "汽车档案信息")
public class CarDossierVO implements Serializable {

    @ApiModelProperty("档案id")
    private Long dossierId;

    @ApiModelProperty(value = "检测日期", example = "2021-05-15")
    private String checkDay;

    @ApiModelProperty("车牌号")
    private String carNumber;
}
