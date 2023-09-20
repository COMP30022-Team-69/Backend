package com.team69.itproject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.dao.SongDAO;
import com.team69.itproject.dao.UserDAO;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.po.UserPO;
import com.team69.itproject.services.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("wyx")
class UserDAOTests {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UsersService usersService;

    @Test
    void testAddUser() {
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        userDAO.addUser("Xuanniu","sb@gmail.com","123456789");
        UserPO userPO = usersService.getOne(
                new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, "Xuanniu")
        );
        assertArrayEquals(userPO.getUsername().toCharArray(),"Xuanniu".toCharArray());
        System.out.println("Pig");
    }

}
