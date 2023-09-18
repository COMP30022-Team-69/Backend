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

    @ApiModelProperty(value="Song Name", notes = "Name of the song in question")
    private String name;

    @ApiModelProperty(value="Song Description", notes ="Text description of the song")
    private String description;

    @ApiModelProperty(value="Song Author", notes ="Author of the song")
    private String author;

    @ApiModelProperty(value="Song Release Date", notes = "Date when the song is released")
    private LocalDate releaseDate;
}
