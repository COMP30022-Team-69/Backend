package com.team69.itproject;

import com.team69.itproject.dao.SongDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SongDAOTests {

    @Autowired
    private SongDAO songDAO;

    @Test
    void testAddSongToUserSongList() {
        System.out.println("Pig");
    }

}
