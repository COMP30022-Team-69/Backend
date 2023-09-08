package com.team69.itproject.entities.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName(value = "category", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Category Persistent Object")
public class CategoryPO {

    @ApiModelProperty("Category ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("Category Name")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("Category Description")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty("Create Time")
    @TableField(value = "create_time")
    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();

    @ApiModelProperty("Song Count")
    @TableField(value = "song_count")
    private Integer songCount = 0;

    @ApiModelProperty(value = "Category state", notes = "0 for disable, 1 for enable")
    @TableLogic
    @TableField(value = "status")
    @Builder.Default
    private boolean status = true;
}
