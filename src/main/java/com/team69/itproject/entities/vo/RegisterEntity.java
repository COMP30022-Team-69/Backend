package com.team69.itproject.entities.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@ApiModel("注册信息实体类")
public class RegisterEntity {
    @ApiModelProperty("用户名，长度在1~30之间")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 1, max = 30, message = "无效的长度")
    private String username;

    @ApiModelProperty("密码，长度在6~128位之间")
    @NotEmpty(message = "用户密码不能为空")
    @Length(min = 6, max = 128, message = "无效的长度")
    private String password;

    @ApiModelProperty(value = "到期日期", notes = "格式：yyyy-MM-dd HH:mm:ss")
    @Future(message = "仅可为将来日期")
    @NotNull(message = "到期时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime validTime;
}
