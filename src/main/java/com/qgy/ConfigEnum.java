package com.qgy;

//配置文件
public enum ConfigEnum {
    PATH_NAME(0,"C:\\job"),
    DATE_PATTERN(1,"YYYY-MM-dd"),
    DATE_LOCAL_PATTERN(2,"YYYY-MM-dd HH:mm:ss"),
    DATE_112JM_PATTERN(3,"YYYYMMdd"),
    DATE_112JM_TARGET_PATTERN(4,"YY年MM月dd日"), //加盟输出文件名的日期格式
    DATE_112TAGET_PATTERN(5,"YYYY/MM/dd"),

    ZY_THEME(20,"ZY"),
    JM_THEME(21,"JM")
    ;

    private Integer code;
    private String message;

    ConfigEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
