package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 检测方案表
 * @author liuyang
 * @date 2023年10月10日 13:59
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("x_wvid_base_50_org_plan_list")
public class OrgPlanList {
    /**
     * 方案Id
     */
    @TableField("planId")
    private Long planId;

    /**
     * 机构Id
     */
    @TableField("orgId")
    private Long orgId;

    /**
     * 是否有效 0 无效 1 有效
     */
    @TableField("isEffective")
    private Short isEffective;
}
