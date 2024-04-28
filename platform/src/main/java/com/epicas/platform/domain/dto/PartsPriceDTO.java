package com.epicas.platform.domain.dto;

import com.epicas.platform.utils.ExcelImport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 接收excel导入数据
 * @author liuyang
 * @date 2023年09月28日 16:51
 */
@Data
@ApiModel(description = "批量添加配件价格")
public class PartsPriceDTO implements Serializable {

    @ExcelImport(value = "部位id", required = true)
    @ApiModelProperty(value = "配件信息所属部位id")
    private Integer positionId;

    @ExcelImport(value = "配件名", required = true)
    @ApiModelProperty(value = "配件名")
    private String partsName;

    @ExcelImport(value = "配件编号", required = true)
    @ApiModelProperty(value = "配件编号")
    private String partsNo;

    @ExcelImport(value = "配件单价", required = true)
    @ApiModelProperty(value = "配件单价：分")
    private Double price;

    @ExcelImport(value = "配件品牌")
    @ApiModelProperty(value = "配件品牌")
    private String brandName;

    @ExcelImport(value = "配件/工时",kv = "0-配件;1-工时", required = true)
    @ApiModelProperty(value = "配件信息类型：0-配件，1-工时")
    private Integer showType;
}
