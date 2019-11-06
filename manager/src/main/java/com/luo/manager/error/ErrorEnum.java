package com.luo.manager.error;

public enum ErrorEnum {
    ID_NOT_NULL("F001", "id不能为空", false),
    UNKNOWN("999", "unknown", false),

    ;
    private String code;
    private String desc;
    private boolean canRetry;

    ErrorEnum(String code, String desc, boolean canRetry) {
        this.code = code;
        this.desc = desc;
        this.canRetry = canRetry;
    }

    public static ErrorEnum getByCode(String code) {
        for (ErrorEnum value : ErrorEnum.values()) {

            if (value.code.equals(code)) {
                return value;
            }

        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isCanRetry() {
        return canRetry;
    }
}
