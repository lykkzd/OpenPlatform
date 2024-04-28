package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 零配件数据表
 * @author liuyang
 * @date 2023年09月28日 17:02
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("x_wvid_e_parts_price")
public class PartsPrice {

    @TableId(value = "partsId", type = IdType.AUTO)
    private Long partsId;

    @TableField("orgId")
    private Long orgId;

    @TableField("positionId")
    private Long positionId;

    @TableField("showType")
    private Integer showType;

    @TableField("partsName")
    private String partsName;

    @TableField("partsNo")
    private String partsNo;

    @TableField("price")
    private Double price;

    @TableField("defaultNum")
    private BigDecimal defaultNum;

    @TableField("brandName")
    private String brandName;

    @TableField("modelName")
    private String modelName;
}
