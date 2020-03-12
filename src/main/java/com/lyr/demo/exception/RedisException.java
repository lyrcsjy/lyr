package com.lyr.demo.exception;

import com.lyr.demo.response.ResponseError;

/**
 * @Author: lyr
 * @Description: redis错误相关
 * @Date: 2020/03/12 6:02 下午
 * @Version: 1.0
 **/
public class RedisException extends RuntimeException {
    private static final int REDIS_ERROR_CODE = 2001;
    private ResponseError responseError;

    public RedisException(String message) {
        super(message);
        ResponseError error = new ResponseError();
        error.setCode(2001);
        error.setMessage(message);
        this.responseError = error;
    }

    public RedisException(Throwable cause) {
        super(cause);
    }

    public ResponseError getResponseError() {
        return this.responseError;
    }

    public void setResponseError(ResponseError responseError) {
        this.responseError = responseError;
    }
}
