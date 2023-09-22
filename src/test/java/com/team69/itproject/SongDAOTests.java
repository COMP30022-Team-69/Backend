package com.team69.itproject;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.dao.SongDAO;
import com.team69.itproject.dao.UserDAO;
import com.team69.itproject.entities.dto.SongDTO;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.po.SongPO;
import com.team69.itproject.entities.po.UserPO;
import com.team69.itproject.entities.vo.SongVO;
import com.team69.itproject.services.SongService;
import com.team69.itproject.services.UsersService;
import org.apache.logging.log4j.core.util.PasswordDecryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("lb")
class SongDAOTests {

    @Autowired
    private SongDAO songDAO;
    @Autowired
    private SongService songService;

    @Test
    @Transactional
    @Rollback
    void testGetSongById() {
        String songName = "ABC";
        SongVO songVO = new SongVO(songName,"aaa","DEF", LocalDate.now());
        SongPO newSong = new SongPO();
        BeanUtil.copyProperties(songVO, newSong);
        songService.save(newSong);

        Page<SongDTO> songPOPage = new Page<>(0, 20);
        Page<SongDTO> songs = songService.searchSongByName(songPOPage, songName);
        long id = songs.getRecords().get(0).getId();

        SongDTO song = songDAO.getSongById(id);
        System.out.println(song.getName());
    }

    void deleteAllSong(){
        songService.remove(new QueryWrapper<>());
    }

}
