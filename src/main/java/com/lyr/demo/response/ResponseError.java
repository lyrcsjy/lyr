package com.lyr.demo.response;

import com.lyr.demo.utils.StrUtils;
import lombok.NoArgsConstructor;

/**
 * @Author: lyr
 * @Description: Response错误
 * @Date: 2020/03/12 6:05 下午
 * @Version: 1.0
 **/
@NoArgsConstructor
public class ResponseError extends RestfulResponse{

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder(StrUtils.format("【{}】{}", super.getCode() ,super.getMessage()));
        return msg.toString();
    }
}
