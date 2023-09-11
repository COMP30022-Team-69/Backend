package com.team69.itproject.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.aop.annotations.UserAuth;
import com.team69.itproject.aop.enums.AccessLevel;
import com.team69.itproject.dao.SongDAO;
import com.team69.itproject.entities.bo.ResponseEntity;
import com.team69.itproject.entities.dto.SongDTO;
import com.team69.itproject.entities.vo.AddSongToListVO;
import com.team69.itproject.entities.vo.SongVO;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Api(tags = "Song APIs")
@RestController
@RequestMapping("/song")
public class SongController {
    private final List<String> listName = Arrays.asList("Background", "Sleep", "Relax", "Favorite");
    @Resource
    private SongDAO songDAO;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin', 'normal')")
    public ResponseEntity<Page<SongDTO>> getSongList(@RequestParam int page,
                                                     @RequestParam int size) {
        Page<SongDTO> songList = songDAO.getSongList(page, size);
        if (songList == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(songList);
    }

    @GetMapping("/search/name")
    @PreAuthorize("hasAnyAuthority('admin', 'normal')")
    public ResponseEntity<Page<SongDTO>> getSongByName(@RequestParam int page,
                                                       @RequestParam int size,
                                                       @RequestParam String name) {
        Page<SongDTO> songList = songDAO.getSongByName(page, size, name);
        if (songList == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(songList);
    }

    @GetMapping("/list/category")
    @PreAuthorize("hasAnyAuthority('admin', 'normal')")
    public ResponseEntity<Page<SongDTO>> getSongByCategory(@RequestParam int page,
                                                           @RequestParam int size,
                                                           @RequestParam String category) {
        Page<SongDTO> songList = songDAO.getSongByCategory(page, size, category);
        if (songList == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(songList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin', 'normal')")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        SongDTO songById = songDAO.getSongById(id);
        if (songById == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(songById);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<String> addSong(@RequestBody SongVO songVO) {
        boolean addSong = songDAO.addSong(songVO);
        if (addSong) {
            return ResponseEntity.ok();
        }
        return ResponseEntity.error(501, "Add song failed");
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<String> updateSong(@PathVariable Long id,
                                             @RequestBody SongVO songVO) {
        boolean updateSong = songDAO.updateSong(id, songVO);
        if (updateSong) {
            return ResponseEntity.ok();
        }
        return ResponseEntity.error(501, "Update song failed");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        boolean deleteSong = songDAO.deleteSong(id);
        if (deleteSong) {
            return ResponseEntity.ok();
        }
        return ResponseEntity.error(501, "Delete song failed");
    }

    @PostMapping("/addSongToList")
    @PreAuthorize("hasAnyAuthority('admin', 'normal')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<String> addSongToList(@RequestHeader("userId") Long userId,
                                                @RequestBody AddSongToListVO addSongToListVO) {
        String songListName = addSongToListVO.getSongListName();
        if (!listName.contains(songListName)) {
            return ResponseEntity.error(501, "Song list name is not allowed");
        }
        songDAO.addSongToUserSongList(userId, songListName, addSongToListVO.getSongIdList());
        return ResponseEntity.ok();
    }

    @PostMapping("/deleteSongFromList")
    @PreAuthorize("hasAnyAuthority('admin', 'normal')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<String> deleteSongFromList(@RequestHeader("userId") Long userId,
                                                     @RequestBody AddSongToListVO addSongToListVO) {
        String songListName = addSongToListVO.getSongListName();
        if (!listName.contains(songListName)) {
            return ResponseEntity.error(501, "Song list name is not allowed");
        }
        songDAO.removeSongFromUserSongList(userId, songListName, addSongToListVO.getSongIdList());
        return ResponseEntity.ok();
    }
}
