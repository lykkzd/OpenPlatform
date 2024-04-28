package com.epicas.platform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO用于接收前端参数
 * @author liuyang
 * @date 2023年10月10日 15:26
 */
@Data
@ApiModel(description = "添加员工车辆白名单")
public class AddOrgCarWhiteListDTO implements Serializable {

    @ApiModelProperty(value = "车牌号", required = true)
    @NotNull(message = "车牌号不能为空")
    private String carNumber;

    @ApiModelProperty("车架号(可以不传，传就更新)")
    private String frameNumber;
}
