package com.youqi.usercenter.common;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功返回
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "ok", "");
    }

    /**
     * 通用失败（根据枚举）
     */
    public static BaseResponse<?> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 自定义错误返回（纯自定义）
     */
    public static BaseResponse<?> error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }

    /**
     * 自定义 message + description（基于枚举错误码）
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    /**
     * 自定义 description（基于枚举错误码）
     */
    public static BaseResponse<?> error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), description);
    }
}
