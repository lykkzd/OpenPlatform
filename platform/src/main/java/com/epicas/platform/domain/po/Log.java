package com.epicas.platform.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 日志表
 * @author liuyang
 * @date 2023年10月09日 11:08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("x_wvid_open_platform_log")
public class Log {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("url")
    private String url;

    @TableField("operation")
    private String operation;

    @TableField("parameter")
    private String parameter;

    @TableField("className")
    private String className;

    @TableField("method")
    private String method;

    @TableField("loginIp")
    private String loginIp;

    @TableField("operTime")
    private Long operTime;
}
