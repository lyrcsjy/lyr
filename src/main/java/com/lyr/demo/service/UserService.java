package com.lyr.demo.service;

import com.lyr.demo.domain.User;
import com.lyr.demo.vo.UserAddVo;

/**
 * @Author: lyr
 * @Description: 用户相关接口
 * @Date: 2020/03/12 9:05 下午
 * @Version: 1.0
 **/
public interface UserService extends BaseService<User,String> {
    /**
     * 用户注册
     * @param userAddVo
     */
    void register(UserAddVo userAddVo);
}
