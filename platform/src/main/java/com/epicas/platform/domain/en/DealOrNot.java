package com.epicas.platform.domain.en;

/**
 * @author liuyang
 * 日期2023-10-07  16:29
 */
public enum DealOrNot implements BaseEnum{
    /**
     * 成交
     */
    DEAL(1,"成交"),

    /**
     * 未成交
     */
    NOT_DEAL(0,"未成交");
    private Integer code;
    private String message;

    DealOrNot(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static DealOrNot getByCode(Integer code){
        for (DealOrNot value : DealOrNot.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    /**
     * 根据message获取枚举
     * @param message
     * @return
     */
    public static DealOrNot getByMessage(String message){
        for (DealOrNot value : DealOrNot.values()) {
            if (value.getMessage().equals(message)){
                return value;
            }
        }
        return null;
    }
}
