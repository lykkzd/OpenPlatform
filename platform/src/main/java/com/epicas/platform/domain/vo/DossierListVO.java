package com.epicas.platform.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * VO用于给前端返回数据
 * @author liuyang
 * @date 2023年10月08日 17:33
 */
@Data
@ApiModel(description = "档案列表信息")
public class DossierListVO implements Serializable {

    @ApiModelProperty(value = "车牌号(可能没有)")
    private String carNumber;

    @ApiModelProperty(value = "检测日期", example = "2021-05-15")
    private String checkDay;

    @ApiModelProperty("key：档案id value：当前档案下的部位id列表")
    private Map<Long, List<Long>> dossierMap;
}
