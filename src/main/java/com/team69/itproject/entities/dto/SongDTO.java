package com.team69.itproject.entities.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {

    @ApiModelProperty("Song ID")
    private Long id;

    @ApiModelProperty("Song Name")
    private String name;

    @ApiModelProperty("Song Description")
    private String description;

    @ApiModelProperty("Song Author")
    private String author;

    @ApiModelProperty("Song Release Date")
    private LocalDate releaseDate;

    @ApiModelProperty("Song Create Time")
    @Builder.Default
    private LocalDate createTime = LocalDate.now();

    @ApiModelProperty(value = "Song state", notes = "0 for disable, 1 for enable")
    @Builder.Default
    private boolean status = true;
}
