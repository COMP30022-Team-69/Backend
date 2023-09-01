package com.team69.itproject.entities.po;

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

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @TableName users
 */
@TableName(value = "users", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("User Persistent Object")
public class UserPO implements Serializable, UserDetails {
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * User ID
     */
    @ApiModelProperty("User ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * Username
     * Unique, used for login
     */
    @ApiModelProperty(value = "Username", notes = "Unique, used for login")
    @TableField(value = "username")
    private String username;
    /**
     * User password
     */
    @ApiModelProperty("User password")
    @TableField(value = "password")
    private String password;
    /**
     * User Email
     */
    @ApiModelProperty("User Email")
    @TableField(value = "email")
    private String email;
    /**
     * Account state
     * 0 for disable, 1 for enable
     */
    @ApiModelProperty(value = "Account state", notes = "0 for disable, 1 for enable")
    @TableLogic(value = "1", delval = "0")
    @TableField(value = "status")
    @Builder.Default
    private boolean status = true;
    /**
     * Created time
     */
    @ApiModelProperty("Created time")
    @TableField(value = "create_time")
    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * User remark
     */
    @ApiModelProperty("User remark")
    @TableField(value = "remark")
    private String remark = "";
    /**
     * If the account is expired
     */
    @ApiModelProperty("If the account is expired")
    @TableField(value = "expired")
    @Builder.Default
    private boolean expired = false;
    /**
     * If the account is locked
     */
    @ApiModelProperty("If the account is locked")
    @TableField(value = "locked")
    @Builder.Default
    private boolean locked = false;
    /**
     * If the credentials is expired
     */
    @ApiModelProperty("If the credentials is expired")
    @TableField(value = "credentials_expired")
    @Builder.Default
    private boolean credentialsExpired = false;
    /**
     * User authorities
     */
    @ApiModelProperty("User authorities")
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
