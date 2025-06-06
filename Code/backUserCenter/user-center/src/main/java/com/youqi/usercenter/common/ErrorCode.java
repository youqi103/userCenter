package com.youqi.usercenter.common;

public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求参数为空", ""),
    NOT_LOGIN_ERROR(400100, "没有登录", ""),
    NO_AUTH_ERROR(400101, "无权限", ""),
    SYSTEM_ERROR(500000, "系统异常", "");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息（简要）
     */
    private final String message;

    /**
     * 详细描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
