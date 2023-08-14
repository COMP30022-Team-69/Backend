package com.team69.itproject.entities;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.*;
import com.team69.itproject.handlers.SimpleGrantTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @TableName users
 */
@TableName(value = "users", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体类")
public class UsersEntity implements Serializable, UserDetails {
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户登录账号
     */
    @ApiModelProperty(value = "用户名", notes = "唯一，用于登陆")
    @TableField(value = "username")
    private String username;
    /**
     * 用户登陆密码
     */
    @ApiModelProperty("用户登陆密码")
    @TableField(value = "password")
    private String password;
    /**
     * 状态，0禁用，1启用
     */
    @ApiModelProperty(value = "账户状态", notes = "0禁用，1启用")
    @TableLogic(value = "1", delval = "0")
    @TableField(value = "status")
    @Builder.Default
    private boolean status = true;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 用户备注
     */
    @ApiModelProperty("用户备注")
    @TableField(value = "remark")
    private String remark = "";
    /**
     * 是否过期
     */
    @ApiModelProperty("是否过期")
    @TableField(value = "expired")
    @Builder.Default
    private boolean expired = false;
    /**
     * 是否封禁
     */
    @ApiModelProperty("是否封禁")
    @TableField(value = "locked")
    @Builder.Default
    private boolean locked = false;
    /**
     * 凭证是否过期
     */
    @ApiModelProperty("凭证是否过期")
    @TableField(value = "credentials_expired")
    @Builder.Default
    private boolean credentialsExpired = false;
    /**
     * 用户权限
     */
    @ApiModelProperty("用户权限")
    @TableField(value = "authorities", typeHandler = SimpleGrantTypeHandler.class)
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
