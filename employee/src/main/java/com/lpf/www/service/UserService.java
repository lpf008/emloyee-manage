package com.lpf.www.service;

import com.lpf.www.entities.Result;
import com.lpf.www.entities.User;

/**
 * @auther zzyy
 * @create 2023-06-18 0:22
 */
public interface UserService {
    public Result login(User user);

    public Result regist(User user);
}
