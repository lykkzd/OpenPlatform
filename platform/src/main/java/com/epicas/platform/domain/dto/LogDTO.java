package com.epicas.platform.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO用于接收前端参数
 * @author liuyang
 * @date 2023年10月09日 11:14
 */
@Data
@ApiModel(description = "日志收集")
public class LogDTO implements Serializable {

    private Long id;

    private String url;

    private String operation;

    private String parameter;

    private String className;

    private String method;

    private String loginIp;

    private Long operTime;

}
