package com.youqi.usercenter.common;

import lombok.Data;

import java.io.Serializable;


/**
 * 通用返回类
 * @param <T>
 * @author by youqi
 */
@Data
public class BaseResponse<T>implements Serializable {
    private  int code;
    private  T data;
    public  String message;


    public  BaseResponse(int code,T data,String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code, T data) {
        this(code,data,"");
    }
}
