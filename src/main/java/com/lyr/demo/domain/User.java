package com.lyr.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: lyr
 * @Description: 用户实体类
 * @Date: 2020/03/12 7:52 下午
 * @Version: 1.0
 **/
@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class User extends BaseDomain {
    @Column(name = "name", columnDefinition = "varchar(32) default NULL COMMENT'用户名'")
    private String name;

    @Column(name = "password", columnDefinition = "varchar(32) default NULL COMMENT'密码'")
    private String password;
}
