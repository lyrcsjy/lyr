package com.lyr.demo.exception;

import com.lyr.demo.response.ResponseError;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lyr
 * @Description: rest异常处理
 * @Date: 2020/03/12 8:56 下午
 * @Version: 1.0
 **/
public class RestException extends RuntimeException {

    @Getter
    @Setter
    private ResponseError responseError;

    public RestException(ResponseError responseError) {
        super(responseError.toString());
        this.responseError = responseError;
    }
}