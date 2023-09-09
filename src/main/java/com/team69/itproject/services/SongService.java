package com.team69.itproject.services;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team69.itproject.entities.dto.SongDTO;
import com.team69.itproject.entities.po.SongPO;
import com.team69.itproject.entities.vo.SongVO;

public interface SongService extends IService<SongPO> {
    Page<SongDTO> getSongList(Page<SongDTO> songPOPage);

    Page<SongDTO> getSongByCategory(Page<SongDTO> songPOPage, String category);

    Page<SongDTO> searchSongByName(Page<SongDTO> songPOPage, String name);
}