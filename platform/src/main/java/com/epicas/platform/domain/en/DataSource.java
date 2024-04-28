package com.epicas.platform.domain.en;

/**
 * @author liuyang
 * @date 2023年10月11日 17:33
 */
public enum DataSource {
    /**
     * 对接平台第三方调用
     */
    THIRD_PARTY_CALL(1)
    ;
    private final Integer value;

    DataSource(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
