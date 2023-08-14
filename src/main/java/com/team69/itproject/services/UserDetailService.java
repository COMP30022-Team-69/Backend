package com.team69.itproject.services;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.team69.itproject.entities.UsersEntity;
import com.team69.itproject.mappers.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<UsersEntity> queryWrapper = new LambdaQueryWrapper<>();
        UsersEntity usersEntity = usersMapper.selectOne(
                queryWrapper.select(
                                UsersEntity::getId,
                                UsersEntity::getUsername,
                                UsersEntity::getAuthorities,
                                UsersEntity::getPassword,
                                UsersEntity::isExpired,
                                UsersEntity::isStatus,
                                UsersEntity::isLocked,
                                UsersEntity::isCredentialsExpired)
                        .eq(UsersEntity::getUsername, username)
        );
        log.debug(JSON.toJSONString(usersEntity));
        if (usersEntity == null) {
            throw new UsernameNotFoundException("用户名或者密码错误！");
        }
        // 下面抛出的异常 Spring Security 会自动捕获并进行返回
        if (!usersEntity.isEnabled()) {
            throw new DisabledException("此账户已被禁用！");
        } else if (!usersEntity.isAccountNonLocked()) {
            throw new LockedException("此账户已被锁定！");
        } else if (!usersEntity.isAccountNonExpired()) {
            throw new AccountExpiredException("此账户已过期！");
        } else if (!usersEntity.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("此账户凭证已过期！");
        }
        return usersEntity;
    }

}
