package com.team69.itproject.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.dao.SongDAO;
import com.team69.itproject.entities.bo.ResponseEntity;
import com.team69.itproject.entities.dto.SongDTO;
import com.team69.itproject.entities.vo.SongVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/song")
public class SongController {
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

}