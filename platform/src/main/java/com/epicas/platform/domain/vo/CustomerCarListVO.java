package com.epicas.platform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * VO用于给前端返回数据
 * @author liuyang
 * @date 2023年10月09日 16:40
 */
@Data
@ApiModel(description = "顾客车辆信息")
public class CustomerCarListVO implements Serializable {

    @ApiModelProperty("车牌ID(carId)")
    private Long id;

    @ApiModelProperty("车牌号")
    private String carNumber;

    @ApiModelProperty("车架号")
    private String frameNumber;

}
