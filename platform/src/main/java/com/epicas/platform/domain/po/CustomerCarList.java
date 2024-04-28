package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 车辆信息表
 * @author liuyang
 * @date 2023年10月09日 16:36
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("customer_car_list")
public class CustomerCarList {
    /**
     * carId
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 汽油类型
     */
    @TableField("fuelTypeId")
    private Long fuelTypeId;

    /**
     * 车型Id
     */
    @TableField("modelId")
    private Long modelId;

    /**
     * 车牌号
     */
    @TableField("carNumber")
    private String carNumber;

    /**
     * 车架号
     */
    @TableField("frameNumber")
    private String frameNumber;

    /**
     * 客户姓名
     */
    @TableField("customerName")
    private String customerName;

    /**
     * 客户手机号
     */
    @TableField("customerPhone")
    private String customerPhone;

    /**
     * 初始添加所属的机构ID
     */
    @TableField("initOrgId")
    private Long initOrgId;

    /**
     * 发动机类型
     */
    @TableField("engineId")
    private Integer engineId;

    /**
     * 0 代表未知 1代表男  2代表女
     */
    @TableField("sex")
    private Integer sex;

    @TableField("vinParse")
    private Integer vinParse;
}
