package com.team69.itproject.entities.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("User Data Transfer Object")
public class UsersDTO implements Serializable {
    /**
     * User ID
     */
    @ApiModelProperty("User ID")
    private Long id;
    /**
     * Username
     * Unique, used for login
     */
    @ApiModelProperty(value = "Username", notes = "Unique, used for login")
    private String username;
    /**
     * User email
     */
    @ApiModelProperty("User Email")
    private String email;
    /**
     * Account state
     * 0 for disable, 1 for enable
     */
    @ApiModelProperty(value = "Account state", notes = "0 for disable, 1 for enable")
    private boolean status;
    /**
     * Created time
     */
    @ApiModelProperty("Created time")
    private LocalDateTime createTime;
    /**
     * User remark
     */
    @ApiModelProperty("User remark")
    private String remark;
    /**
     * User authorities
     */
    @ApiModelProperty("User authorities")
    private List<SimpleGrantedAuthority> authorities;
}
