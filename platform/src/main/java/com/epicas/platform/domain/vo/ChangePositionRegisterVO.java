package com.epicas.platform.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author liuyang
 * @date 2023年10月17日 11:14
 */
@Data
public class ChangePositionRegisterVO implements Serializable {

    private Integer successCount;

    private Map<Integer,String> failMap;
}
