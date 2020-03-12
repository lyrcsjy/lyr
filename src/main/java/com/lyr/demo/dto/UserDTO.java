package com.lyr.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Author: lyr
 * @Description: 用户信息展示
 * @Date: 2020/03/12 8:35 下午
 * @Version: 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {
    @ApiModelProperty(value = "用户ID")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(value = "用户名称")
    @JsonProperty("name")
    private String name;
}
