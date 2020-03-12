package com.lyr.demo.exception;

/**
 * @Author: lyr
 * @Description: 枚举反序列化时异常的处理
 * @Date: 2020/03/12 9:36 下午
 * @Version: 1.0
 **/
public class EnumDeserializeException extends RuntimeException {

    public EnumDeserializeException() {
    }

    public EnumDeserializeException(String message) {
        super(message);
    }
}
