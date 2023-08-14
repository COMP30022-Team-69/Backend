package com.team69.itproject.entities.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("用户DTO实体类")
public class UsersDTO {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long id;
    /**
     * 用户登录账号
     */
    @ApiModelProperty(value = "用户名", notes = "唯一，用于登陆")
    private String username;
    /**
     * 等级ID
     */
    @ApiModelProperty("用户等级ID")
    private Long gradeId;
    /**
     * 状态，0禁用，1启用
     */
    @ApiModelProperty(value = "账户状态", notes = "0禁用，1启用")
    private boolean status;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 用户备注
     */
    @ApiModelProperty("用户备注")
    private String remark;
    /**
     * 用户权限
     */
    @ApiModelProperty("用户权限")
    private List<String> authorities;
}
