package com.lyr.demo.response;

import com.lyr.demo.exception.RestException;
import com.lyr.demo.utils.StrUtils;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * @Author: lyr
 * @Description: 异常响应处理
 * @Date: 2020/03/12 8:52 下午
 * @Version: 1.0
 **/
@Accessors(chain = true)
@NoArgsConstructor
public class ResponseException extends RestfulResponse {

    /**
     * 消息格式化的参数
     */
    private Object[] messageFormatParams;
    /**
     * 详细信息
     */
    private Object[] details;

    /**
     * 创建异常
     * @param restData
     * @return
     */
    public static ResponseException build(@NonNull RestData restData) {
        ResponseException responseException = new ResponseException();
        responseException.setCode(restData.code());
        responseException.setMessage(restData.message());
        return responseException;
    }

    /**
     * 创建异常
     * @param code
     * @param message
     * @return
     */
    public static ResponseException bulid(@NonNull int code , @NonNull String message) {
        ResponseException responseException = new ResponseException();
        responseException.setCode(code);
        responseException.setMessage(message);
        return responseException;
    }

    /**
     * 失败，抛出异常
     * @param restData
     * @param messageFormatParams
     */
    public static void fatalFormatMessage(RestData restData, Object...messageFormatParams){
        build(restData).format(messageFormatParams).fatal();
    }

    /**
     * 失败，抛出异常
     * @param restData
     */
    public static void fatal(RestData restData, Object...details){
        build(restData).details(details).fatal();
    }

    /**
     * 失败，抛出异常
     * @param restData
     * @param details
     */
    public static RestException returnError(RestData restData, Object...details){
        return build(restData).details(details).returnError();
    }

    /**
     * 使用StrUtils.format()格式化输出的消息内容
     * @param messageFormatParams
     * @return
     */
    public ResponseException format(Object ...messageFormatParams){
        this.messageFormatParams = messageFormatParams;
        return this;
    }

    /**
     * 设置详细信息
     * @param details
     * @return
     */
    public ResponseException details(Object... details){
        this.details = details;
        return this;
    }

    /**
     * 失败，抛出异常
     */
    public void fatal(){
        throw returnError();
    }

    /**
     * 返回异常信息
     * @return
     */
    public RestException returnError(){
        ResponseError error = new ResponseError();
        error.setCode(this.getCode());
        if(messageFormatParams != null) {
            error.setMessage(StrUtils.format(this.getMessage(), messageFormatParams));
        }else {
            error.setMessage(this.getMessage());
        }
        error.setDetails(this.details);
        return new RestException(error);
    }
}
