package com.lyr.demo.web;

import com.lyr.demo.dto.UserDTO;
import com.lyr.demo.response.ResponseData;
import com.lyr.demo.service.UserService;
import com.lyr.demo.vo.UserAddVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: lyr
 * @Description: 用户相关
 * @Date: 2020/03/12 8:31 下午
 * @Version: 1.0
 **/
@Slf4j
@Api(value = "/user", tags = "用户相关")
@RestController
@RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.ALL_VALUE})
public class UserController {
    @Autowired
    UserService userService;
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData<UserDTO> register(
            @ApiParam(name = "userAddVo", value = "用户基础信息") @Valid @RequestBody UserAddVo userAddVo) {
        userService.register(userAddVo);
        return ResponseData.ok();
    }
}
