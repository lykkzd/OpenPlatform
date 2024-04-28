package com.epicas.platform.domain.dto;

import com.epicas.platform.utils.ExcelImport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liuyang
 * @date 2023年10月12日 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "部位成交登记")
public class ChangePositionRegisterDTO implements Serializable {

    @ApiModelProperty("档案id")
    @ExcelImport(value = "档案id", required = true)
    private Integer dossierId;

    @ApiModelProperty(value = "部位id")
    @ExcelImport(value = "部位id", required = true)
    private Integer positionId;

    @ApiModelProperty(value = "是否成交：0-未成交 1-成交")
    @ExcelImport(value = "成交/未成交", kv = "0-未成交;1-成交", required = true)
    private Integer dealOrNot;

    private Integer rowNum;

    private String rowTips;
}
