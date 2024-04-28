package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 员工车辆白名单表
 * @author liuyang
 * @date 2023年10月10日 15:19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("staff_car_white_list")
public class StaffCarWhiteList {

    @TableField("org_id")
    private Long orgId;

    @TableField("car_id")
    private Long carId;

    @TableField("create_time")
    private Long createTime;

    @TableField("update_time")
    private Long updateTime;

    @TableField("is_deleted")
    private Integer isDeleted;
}
