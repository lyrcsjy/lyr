package com.lyr.demo.response;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lyr.demo.constants.HttpConstants;
import com.lyr.demo.utils.StrUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @Author: lyr
 * @Description: JSON转OBJECT
 * @Date: 2020/03/12 8:46 下午
 * @Version: 1.0
 **/
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Accessors
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler","orEmptyMap","orEmptyArray","orEmptySet","orEmptyList","orEmpty","orElse","present"}, ignoreUnknown = true)
public class ResponseData<T> extends RestfulResponse{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    private T data;

    /**
     * 返回成功的数据
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> ok(T data) {
        if(data == null){
            return new ResponseData(HttpConstants.OK, HttpConstants.SUCCESS);
        }
        return new ResponseData(HttpConstants.OK, HttpConstants.SUCCESS, data);
    }

    /**
     * 返回成功的数据
     * @return
     */
    public static <T> ResponseData<T> ok() {
        return ok(null);
    }

    /**
     * 响应的状态码是否为200
     * @return
     */
    public boolean isOk(){
        return HttpConstants.OK == getCode();
    }

    /**
     * 是否data属性不为空
     * @return
     */
    @JsonIgnore
    public boolean isPresent(){
        return Optional.ofNullable(data).isPresent();
    }

    /**
     * 获取data，不存在则返回默认值
     * @return
     */
    @JsonIgnore
    public T getOrElse(T t){
        return Optional.ofNullable(data).orElse(t);
    }

    /**
     * 获取data，不存在则返回空字符串
     * @return
     */
    @JsonIgnore
    public T getOrEmpty(){
        return getOrElse((T) "");
    }

    /**
     * 获取data，不存在则返回空list
     * @return
     */
    @JsonIgnore
    public T getOrEmptyList(){
        return getOrElse((T) Lists.newLinkedList());
    }

    /**
     * 获取data，不存在则返回空set
     * @return
     */
    @JsonIgnore
    public T getOrEmptySet(){
        return getOrElse((T) Sets.newHashSet());
    }

    /**
     * 获取data，不存在则返回空map
     * @return
     */
    @JsonIgnore
    public T getOrEmptyArray(){
        return getOrElse((T) new Object[0]);
    }

    /**
     * 如果log日志是debug级别，则输出
     * 推荐使用！！！尽量不输出info级别的日志
     * @return
     */
    public ResponseData<T> printDebugLog(){
        if(log.isDebugEnabled()){
            log.debug(JSONObject.toJSONString(this));
        }
        return this;
    }

    /**
     * 如果log日志是debug级别，则输出
     * 推荐使用！！！尽量不输出info级别的日志
     * @return
     */
    public ResponseData<T> printInfoLog(){
        if(log.isInfoEnabled()){
            log.info(JSONObject.toJSONString(this));
        }
        return this;
    }

    /**
     * 如果不为ok的话，则抛出异常信息
     * @return
     */
    @JsonIgnore
    public ResponseData<T> ifNotOkThrow(){
        return ifNotOkThrow(getMessage());
    }

    /**
     * 如果不为ok的话，则抛出异常信息
     * @return
     */
    @JsonIgnore
    public ResponseData<T> ifNotOkThrow(String message){
        if(!isOk()) {
            ResponseException.bulid(getCode(), StrUtils.notBlank(message) ? message : getMessage()).details(getDetails()).fatal();
        }
        return this;
    }

    /**
     * 如果不为ok的话，则抛出异常信息
     * @return
     */
    @JsonIgnore
    public void ifNotOkThrow(RestData restData, String...formatMessages){
        if(!isOk()) {
            ResponseException.build(restData).format().format(formatMessages).fatal();
        }
    }

    /**
     * 获取data，不存在则返回空数组
     * @return
     */
    @JsonIgnore
    public T getOrEmptyMap(){
        return getOrElse((T) Maps.newConcurrentMap());
    }

    public ResponseData(@NonNull int code, @NonNull String message) {
        super(code, message, "");
    }

    public ResponseData(@NonNull int code,@NonNull String message, T data) {
        super(code, message, "");
        this.data = data;
    }

    public ResponseData(@NonNull int code,@NonNull String message, T data,String details) {
        super(code, message, details);
        this.data = data;
    }
}