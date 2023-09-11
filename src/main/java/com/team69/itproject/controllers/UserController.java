package com.team69.itproject.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.aop.annotations.UserAuth;
import com.team69.itproject.aop.enums.AccessLevel;
import com.team69.itproject.dao.UserDAO;
import com.team69.itproject.entities.bo.ResponseEntity;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.vo.RegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "User APIs")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserDAO userDAO;

    @ApiOperation("Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterVO registerVO) {
        boolean registerSucceed = userDAO.addUser(
                registerVO.getUsername(),
                registerVO.getEmail(),
                registerVO.getPassword()
        );
        if (registerSucceed) {
            return ResponseEntity.ok();
        }
        return ResponseEntity.error(501, "Register failed");
    }

    @ApiOperation(value = "Get a user by username", authorizations = {@Authorization("normal"), @Authorization("admin")})
    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('normal', 'admin')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<UsersDTO> getUserByUsername(@PathVariable String username) {
        UsersDTO userByUsername = userDAO.getUserByUsername(username);
        if (userByUsername == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(userByUsername);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('normal', 'admin')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<UsersDTO> getUserById(@RequestHeader("userId") Long userId) {
        UsersDTO userById = userDAO.getUserById(userId);
        if (userById == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(userById);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<Page<UsersDTO>> getUserList(@RequestParam int page,
                                                      @RequestParam int size) {
        Page<UsersDTO> userList = userDAO.getUserList(page, size);
        if (userList == null) {
            return ResponseEntity.error(404, null);
        }
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/update/email")
    @PreAuthorize("hasAnyAuthority('normal', 'admin')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<String> updateEmail(@RequestHeader("userId") Long userId, @RequestBody String email) {
        UsersDTO userById = userDAO.getUserById(userId);
        if (userById == null) {
            return ResponseEntity.error(404, null);
        }
        userDAO.updateUserEmail(userId, email);
        return ResponseEntity.ok();
    }

    @PostMapping("/update/password")
    @PreAuthorize("hasAnyAuthority('normal', 'admin')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<String> updatePassword(@RequestHeader("userId") Long userId, @RequestBody String password) {
        UsersDTO userById = userDAO.getUserById(userId);
        if (userById == null) {
            return ResponseEntity.error(404, null);
        }
        userDAO.updateUserPassword(userId, password);
        return ResponseEntity.ok();
    }
}
