package com.lyr.demo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: lyr
 * @Description: 删除状态枚举
 * @Date: 2020/03/12 9:29 下午
 * @Version: 1.0
 **/
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DelStatus {
    /**
     * 删除状态
     */
    UN_DELETE(0,"未删除"),
    DELETE(1,"已删除");


    private int code;
    private String name;
}
