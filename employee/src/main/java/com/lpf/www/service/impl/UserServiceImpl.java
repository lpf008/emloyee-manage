package com.lpf.www.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lpf.www.entities.Result;
import com.lpf.www.entities.User;
import com.lpf.www.mapper.UserMapper;
import com.lpf.www.service.UserService;
import com.lpf.www.utils.JwtHelper;
import com.lpf.www.utils.MD5Util;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private UserMapper userMapper;

    /**
     * 登录业务实现
     *
     * @param user
     * @return result封装
     */
    @Override
    public Result login(User user) {

        //根据账号查询
        User loginUser = userMapper.searchUser(user);

        //账号判断
        if (loginUser == null) {
            //账号错误
            return Result.fail("账号错误");
        }

        //判断密码
        if (!StringUtils.isEmpty(user.getPassword())
                && loginUser.getPassword().equals(MD5Util.getMd5(user.getPassword()))) {
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getId()));

            Map data = new HashMap();
            data.put("token", token);

            return Result.ok(data);
        }

        //密码错误
        return Result.fail("密码错误");
    }

    @Override
    public Result regist(User user) {
        User usedUser = userMapper.searchUser(user);

        if (usedUser != null) {
            return Result.fail("用户名已被使用");
        }

        user.setPassword(MD5Util.getMd5(user.getPassword()));
        userMapper.registUser(user);
        return Result.ok("注册成功");
    }
}