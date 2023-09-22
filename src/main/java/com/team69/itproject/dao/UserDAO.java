package com.team69.itproject.dao;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.po.UserPO;
import com.team69.itproject.services.UsersService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDAO {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UsersService usersService;

    public boolean addUser(String username, String email, String password) {
        UserPO newUser = UserPO.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authorities(List.of(new SimpleGrantedAuthority("normal")))
                .build();
        return usersService.save(newUser);
    }

    @Cacheable(value = "users", key = "'-list-'+#page+'-'+#size")
    public Page<UsersDTO> getUserList(int page, int size) {
        Page<UsersDTO> usersDTOPage = new Page<>(page, size);
        return usersService.getUserList(usersDTOPage);
    }

    @Cacheable(value = "users", key = "#username")
    public UsersDTO getUserByUsername(String username) {
        UserPO userPO = usersService.getOne(
                new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, username)
        );
        if (userPO == null) {
            return null;
        }
        UsersDTO usersDTO = new UsersDTO();
        BeanUtil.copyProperties(userPO, usersDTO);
        return usersDTO;
    }

    @Cacheable(value = "users", key = "#id")
    public UsersDTO getUserById(Long id) {
        UserPO userPO = usersService.getById(id);
        UsersDTO usersDTO = new UsersDTO();
        BeanUtil.copyProperties(userPO, usersDTO);
        return usersDTO;
    }

    @CacheEvict(value = "users", key = "#id")
    public void updateUserEmail(Long id, String email) {
        UserPO userPO = usersService.getById(id);
        userPO.setEmail(email);
        usersService.saveOrUpdate(userPO);
    }

    @CacheEvict(value = "users", key = "#id")
    public void updateUserPassword(Long id, String password) {
        UserPO userPO = usersService.getById(id);
        userPO.setPassword(passwordEncoder.encode(password));
        usersService.saveOrUpdate(userPO);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        usersService.removeById(id);
    }

}
