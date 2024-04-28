package com.epicas.platform.domain.en;

/**
 * @author liuyang
 * 日期2023-10-07  20:17
 */
public enum IsEffective {
    /**
     * 有效
     */
    EFFECTIVE((short)1),
    /**
     * 无效
     */
    INVALID((short)0);

    private final Short value;

    IsEffective(Short value) {
        this.value = value;
    }

    public Short getValue() {
        return value;
    }
}
