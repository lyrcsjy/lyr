package com.lyr.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author: lyr
 * @Description: 用户注册信息
 * @Date: 2020/03/12 8:39 下午
 * @Version: 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class UserAddVo implements Serializable {
    @NotBlank(message = "用户名不能为空!")
    @JsonProperty("name")
    @ApiModelProperty(value = "用户名")
    private String name;

    @NotBlank(message = "密码不能为空!")
    @JsonProperty("password")
    @ApiModelProperty(value = "密码")
    private String password;
}
