package com.lpf.www.controller;

import com.lpf.www.entities.Result;
import com.lpf.www.entities.User;
import com.lpf.www.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vue-element-admin/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录需求
     * 地址: /user/login
     * 方式: post
     * 参数:
     * {
     * "username":"zhangsan", //用户名
     * "userPwd":"123456"     //明文密码
     * }
     * 返回:
     * {
     * "code":"200",         // 成功状态码
     * "message":"success"   // 成功状态描述
     * "data":{
     * "token":"... ..." // 用户id的token
     * }
     * }
     * <p>
     * 大概流程:
     * 1. 账号进行数据库查询 返回用户对象
     * 2. 对比用户密码(md5加密)
     * 3. 成功,根据userId生成token -> map key=token value=token值 - result封装
     * 4. 失败,判断账号还是密码错误,封装对应的枚举错误即可
     */
    @Operation(summary = "登录接口", description = "登录", parameters = {
            @Parameter(name = "user", description = "用户账号密码实体类"),
    },
            responses = {
                    @ApiResponse(description = "返回结果", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))
            })
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user);
        return result;
    }

    @Operation(summary = "注册接口", description = "注册", parameters = {
            @Parameter(name = "user", description = "用户账号密码实体类"),
    },
            responses = {
                    @ApiResponse(description = "返回结果", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class)))
            })
    @PostMapping("regist")
    public Result regist(@RequestBody User user) {
        Result result = userService.regist(user);
        return result;
    }
}

