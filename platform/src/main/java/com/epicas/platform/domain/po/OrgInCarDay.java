package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * PO是和表对应的实体类
 * @author liuyang
 * @date 2023年10月07日 17:32
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("org_incar_day")
public class OrgInCarDay implements Serializable {

    @TableField("orgId")
    private Long orgId;

    @TableField("dayIndex")
    private String dayIndex;

    @TableField("carCount")
    private Integer carCount;

    @TableField("oilFilterBase")
    private Integer oilFilterBase;

    @TableField("updateType")
    private Integer updateType;

    @TableField("updateUser")
    private Long updateUser;

    @TableField("updateTime")
    private Long updateTime;
}
