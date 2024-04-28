package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 车辆部位最后成交情况表
 * @author liuyang
 * @date 2023年10月10日 17:39
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("position_final_transaction")
public class PositionFinalTransaction {

    @TableField("org_id")
    private Long orgId;

    @TableField("car_id")
    private Long carId;

    @TableField("position_id")
    private Long positionId;

    @TableField("transaction_time")
    private Long transactionTime;

    @TableField("data_source")
    private Integer dataSource;

}
