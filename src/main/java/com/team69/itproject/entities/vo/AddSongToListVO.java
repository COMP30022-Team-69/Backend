package com.team69.itproject.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel("Add song to list Value Object")
public class AddSongToListVO {
    @ApiModelProperty("Song list name")
    @NotEmpty(message = "Song list name should not be empty!")
    private String songListName;
    @ApiModelProperty("A list of song ids")
    @NotEmpty(message = "Song id list should not be empty!")
    private List<Long> songIdList;
}
