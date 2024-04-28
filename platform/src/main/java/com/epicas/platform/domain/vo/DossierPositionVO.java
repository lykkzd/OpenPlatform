package com.epicas.platform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * VO用于给前端返回数据
 * @author liuyang
 * @date 2023年10月09日 16:50
 */
@Data
@ApiModel(description = "档案部位信息")
public class DossierPositionVO implements Serializable {

    @ApiModelProperty("部位id")
    private Long positionId;

    @ApiModelProperty("诊断结果")
    private String diagnosis;

    @ApiModelProperty(value = "第二版状态：0-忽略，100-正常，110-下次再检测，120-紧急本次处理")
    private Integer positionCheckTag;
}
