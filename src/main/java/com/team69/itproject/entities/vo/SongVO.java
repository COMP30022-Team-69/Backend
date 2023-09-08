package com.team69.itproject.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Song Value Object")
public class SongVO {

    @ApiModelProperty("Song Name")
    private String name;

    @ApiModelProperty("Song Description")
    private String description;

    @ApiModelProperty("Song Author")
    private String author;

    @ApiModelProperty("Song Release Date")
    private LocalDate releaseDate;
}
