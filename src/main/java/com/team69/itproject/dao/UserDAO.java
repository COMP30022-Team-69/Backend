package com.team69.itproject.dao;

import cn.hutool.core.bean.BeanUtil;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.po.UserPO;
import com.team69.itproject.services.UsersService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDAO {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UsersService usersService;

    @CachePut(value = "users", key = "#username")
    public boolean addUser(String username, String email, String password) {
        UserPO newUser = UserPO.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authorities(List.of(new SimpleGrantedAuthority("normal")))
                .build();
        return usersService.save(newUser);
    }

    public UsersDTO getUserByUsername(String username) {
        UserPO userPO = usersService.getById(
                usersService.lambdaQuery()
                        .eq(UserPO::getUsername, username)
        );
        if (userPO == null) {
            return null;
        }
        UsersDTO usersDTO = new UsersDTO();
        BeanUtil.copyProperties(userPO, usersDTO);
        return usersDTO;
    }


}
