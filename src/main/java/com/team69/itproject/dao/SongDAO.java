package com.team69.itproject.dao;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.entities.dto.SongDTO;
import com.team69.itproject.entities.po.SongPO;
import com.team69.itproject.entities.vo.SongVO;
import com.team69.itproject.services.SongService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SongDAO {
    @Resource
    private SongService songService;

    @Cacheable(value = "song", key = "#id")
    public SongDTO getSongById(Long id) {
        SongPO songPO = songService.getById(id);
        SongDTO songDTO = new SongDTO();
        BeanUtil.copyProperties(songPO, songDTO);
        return songDTO;
    }

    @Cacheable(value = "songList", key = "#page+'-'+#size")
    public Page<SongDTO> getSongList(int page, int size) {
        Page<SongDTO> songPOPage = new Page<>(page, size);
        return songService.getSongList(songPOPage);
    }

    @Cacheable(value = "songList", key = "#category+'-'+#page+'-'+#size")
    public Page<SongDTO> getSongByCategory(int page, int size, String category) {
        Page<SongDTO> songPOPage = new Page<>(page, size);
        return songService.getSongByCategory(songPOPage, category);
    }

    @CacheEvict(value = "songList", allEntries = true)
    public boolean addSong(SongVO songVO) {
        SongPO newSong = new SongPO();
        BeanUtil.copyProperties(songVO, newSong);
        return songService.save(newSong);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "songList", allEntries = true),
                    @CacheEvict(value = "song", key = "#id")
            }
    )
    public boolean updateSong(Long id, SongVO songVO) {
        SongPO songPO = songService.getById(id);
        BeanUtil.copyProperties(songVO, songPO);
        return songService.saveOrUpdate(songPO);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "songList", allEntries = true),
                    @CacheEvict(value = "song", key = "#id")
            }
    )
    public boolean deleteSong(Long id) {
        return songService.removeById(id);
    }
}
