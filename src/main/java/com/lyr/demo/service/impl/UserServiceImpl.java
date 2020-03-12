package com.lyr.demo.service.impl;

import com.lyr.demo.domain.User;
import com.lyr.demo.service.UserService;
import com.lyr.demo.vo.UserAddVo;

/**
 * @Author: lyr
 * @Description: 用户相关接口实现
 * @Date: 2020/03/12 9:13 下午
 * @Version: 1.0
 **/
public class UserServiceImpl extends BaseServiceImpl<User,String> implements UserService {
    @Override
    public void register(UserAddVo userAddVo) {
        User user = new User();
        user.setName(userAddVo.getName());
        user.setPassword(userAddVo.getPassword());
        save(user);
    }
}
