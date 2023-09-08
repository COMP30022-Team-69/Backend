package com.team69.itproject.services.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team69.itproject.entities.dto.SongDTO;
import com.team69.itproject.entities.po.SongPO;
import com.team69.itproject.mappers.SongMapper;
import com.team69.itproject.services.SongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, SongPO>
        implements SongService {
    @Resource
    private SongMapper songMapper;

    @Override
    public Page<SongDTO> getSongList(Page<SongDTO> songPOPage) {
        return songMapper.getSongList(songPOPage);
    }

    @Override
    public Page<SongDTO> getSongByCategory(Page<SongDTO> songPOPage, String category) {
        return null;
    }
}
