package com.orange.demo.upmsservice.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 用户角色实体对象。
 *
 * @author Jerry
 * @date 2020-08-08
 */
@Data
@TableName(value = "zz_sys_user_role")
public class SysUserRole {

    /**
     * 用户Id。
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色Id。
     */
    @TableField(value = "role_id")
    private Long roleId;
}
