package com.lpf.www.mapper;

import com.lpf.www.entities.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User loginUser(User user);

    public void registUser(User user);

    public User searchUser(User user);
}