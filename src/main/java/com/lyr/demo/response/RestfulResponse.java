package com.lyr.demo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lyr.demo.constants.HttpConstants;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: lyr
 * @Description: restful形式的response
 * @Date: 2020/03/12 6:06 下午
 * @Version: 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public abstract class RestfulResponse implements Response {

    /**
     * 响应的状态码
     */
    @JsonProperty("code")
    private int code = HttpConstants.OK;

    /**
     * 响应的消息
     */
    @JsonProperty("message")
    private String message = HttpConstants.SUCCESS;

    /**
     * 1.作为响应给前端的详细信息
     * 2.内部服务调用过程中，用于接收远程服务调用异常的详细信息
     */
    @JsonProperty("details")
    private Object details = null;
}
