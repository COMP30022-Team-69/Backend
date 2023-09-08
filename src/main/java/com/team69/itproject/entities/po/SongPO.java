package com.team69.itproject.entities.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @TableName songs
 */
@TableName(value = "songs", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Song Persistent Object")
public class SongPO {

    @ApiModelProperty("Song ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("Song Name")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty("Song Description")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty("Song Author")
    @TableField(value = "author")
    private String author;

    @ApiModelProperty("Song Release Date")
    @TableField(value = "release_date")
    private LocalDate releaseDate;

    @ApiModelProperty("Song Create Time")
    @TableField(value = "create_time")
    @Builder.Default
    private LocalDate createTime = LocalDate.now();

    @ApiModelProperty(value = "Song state", notes = "0 for disable, 1 for enable")
    @TableLogic
    @TableField(value = "status")
    @Builder.Default
    private boolean status = true;

}
