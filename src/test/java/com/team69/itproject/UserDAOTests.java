package com.team69.itproject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.dao.SongDAO;
import com.team69.itproject.dao.UserDAO;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.po.UserPO;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("lb")
class UserDAOTests {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testAddUser() {
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        userDAO.addUser("Xuanniu", "aa@gmail.com", "123456789");
        UserPO userPO = usersService.getOne(
                new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, "Xuanniu")
        );
        assertArrayEquals(userPO.getUsername().toCharArray(), "Xuanniu".toCharArray());
        System.out.println("Pig");
    }

    @Test
    void testGetUserList() {
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        ArrayList<String> names = new ArrayList<>();
        int n = 20;
        for (Integer i = 0; i < n; i++) {
            names.add("Xuanniu" + i.toString());
            UserPO newUser = UserPO.builder()
                    .username("Xuanniu" + (i.toString()))
                    .email("aaa@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .authorities(List.of(new SimpleGrantedAuthority("normal")))
                    .build();
            usersService.save(newUser);
        }
        Page<UsersDTO> pages = userDAO.getUserList(200, 200);
        for (int i = 0; i < n; i++) {
            //System.out.println(pages.getRecords().get(i).getUsername());
            //UsersDTO xn0 = userDAO.getUserByUsername("Xuanniu0");
            assertArrayEquals(pages.getRecords().get(i).getUsername().toCharArray(), names.get(i).toCharArray());
        }
    }

    @Test
    @Transactional
    @Rollback
    void testGetUserByUsername() {
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        UserPO newUser = UserPO.builder()
                .username("Xuanniu")
                .email("aaa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .authorities(List.of(new SimpleGrantedAuthority("normal")))
                .build();
        usersService.save(newUser);
        UsersDTO xn = userDAO.getUserByUsername("xuaniu");
        assert(xn.getUsername().equals("Xuanniu"));
    }

    @Test
    void testGetUserById() {
        String name = "Xuanniu";
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        UserPO newUser = UserPO.builder()
                .username(name)
                .email("aaa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .authorities(List.of(new SimpleGrantedAuthority("normal")))
                .build();
        usersService.save(newUser);
        UserPO xn = usersService.getOne(
                new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, name)
        );
        long id = xn.getId();
        UsersDTO xnn = userDAO.getUserById(id);
        assertArrayEquals(xnn.getUsername().toCharArray(),name.toCharArray());
        System.out.println(xnn.getUsername());
        long fid = id +1;
        UsersDTO failCase = userDAO.getUserById(fid);
        assert(failCase.getUsername()==null);
    }

    @Test
    void testUpdateUserEmail() {
        String name = "Xuanniu";
        String email = "bbb@outlook.com";
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        UserPO newUser = UserPO.builder()
                .username(name)
                .email("aaa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .authorities(List.of(new SimpleGrantedAuthority("normal")))
                .build();
        usersService.save(newUser);
        UserPO xn = usersService.getOne(
                new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, name)
        );
        long id = xn.getId();
        userDAO.updateUserEmail(id,email);
        xn = usersService.getById(id);
        assertArrayEquals(xn.getEmail().toCharArray(),email.toCharArray());
    }

    @Test
    void testUpdateUserPassword() {
        String name = "Xuanniu";
        String password = "cnji257856f";
        String encodedPassword = passwordEncoder.encode(password);
        Page<UsersDTO> page = new Page<>();
        usersService.clearUserList(page);
        UserPO newUser = UserPO.builder()
                .username(name)
                .email("aaa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .authorities(List.of(new SimpleGrantedAuthority("normal")))
                .build();
        usersService.save(newUser);
        UserPO xn = usersService.getOne(
                new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, name)
        );
        long id = xn.getId();
        userDAO.updateUserPassword(id,encodedPassword);
        xn = usersService.getById(id);
        System.out.println(xn.getPassword());
        System.out.println(encodedPassword);
    }

}
