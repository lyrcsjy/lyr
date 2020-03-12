package com.lyr.demo.response;

/**
 * @Author: lyr
 * @Description: restful状态信息
 * @Date: 2020/03/12 8:50 下午
 * @Version: 1.0
 **/
public interface RestData {
    /**
     * 获取响应代码
     * @return
     */
    int code();

    /**
     * 获取响应消息
     * @return
     */
    String message();
}
