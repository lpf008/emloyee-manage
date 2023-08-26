package com.lpf.www.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "用户信息")
public class User {

    private Integer id;

    /**
     * 用户名
     */

    @Schema(title = "用户名字")
    private String username;

    /**
     * 密码
     */
    @Schema(title = "用户密码")
    private String password;

    /**
     * 性别=0女1男
     */
    @Schema(title = "用户性别")
    private Byte sex;

    /**
     * 删除标志=0不删1删除
     */
    @Schema(title = "删除标志=1不删0删除")
    private Byte deleted;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(title = "更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(title = "创建时间")
    private Date createTime;


}
