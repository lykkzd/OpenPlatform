package com.epicas.platform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyang
 * @date 2023年10月17日 11:18
 */
@Data
@ApiModel(description = "档案信息")
public class DossierVO implements Serializable {

    @ApiModelProperty("主键 档案ID")
    private Long dossierId;

    @ApiModelProperty("站点ID")
    private Long orgId;
}
