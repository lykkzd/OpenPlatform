package com.epicas.platform.result;



public enum ResultCodeEnum {
    SUCCESS(true, 20000, "成功"),
    COMMON_FAIL(false, 50000, "失败"),
    MYSQL_OPERATION_EXCEPTION(false, 50001, "mysql数据库操作异常"),
    EMPTY_EFFECTIVE_PLAN_LIST(false, 50002, "该机构下没有有效的方案"),
    CAR_NUMBER_NOT_EXIST(false, 50003, "车牌号不存在"),
    ORG_ID_NOT_EXIST(false, 50004, "请求头中缺少orgId"),
    ENCRYPT_ERROR(false, 50005, "加密失败"),
    PARSE_EXCEL_ERROR(false, 50006, "解析excel失败"),
    EXCEED_IMPORT_SCOPE(false, 50007, "excel批量导入的数量超出1000"),
    DTO_NOT_NULL(false, 50008, "传入数据不能为空"),
    DEAL_OR_NOT_TYPE_ERROR(false, 50009, "成交或未成交字段只能填写0或1"),
    SHOW_TYPE_ERROR(false, 50010, "配件信息字段只能填写0或1"),
    SECRECT_ERROR(false,50020,"响应数据加密失败");
    private Boolean status;
    private Integer code;
    private String message;

    ResultCodeEnum(Boolean status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
