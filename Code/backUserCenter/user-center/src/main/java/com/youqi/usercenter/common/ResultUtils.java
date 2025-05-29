package com.youqi.usercenter.common;


/**
 * 返回工具类
 * @author youqi
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(200,data,"ok");
    }
}
