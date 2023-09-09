package com.team69.itproject.controllers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team69.itproject.aop.annotations.UserAuth;
import com.team69.itproject.aop.enums.AccessLevel;
import com.team69.itproject.dao.UserDAO;
import com.team69.itproject.entities.bo.ResponseEntity;
import com.team69.itproject.entities.dto.UsersDTO;
import com.team69.itproject.entities.vo.RegisterVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserDAO userDAO;

    // https://locahost:8081/user/register
    // {username: "test", password: "test", email: "test"}
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
    public ResponseEntity<String> updateEmail(@RequestBody String email) {
        return ResponseEntity.ok();
    }

    @PostMapping("/update/password")
    @PreAuthorize("hasAnyAuthority('normal', 'admin')")
    @UserAuth(AccessLevel.SELF)
    public ResponseEntity<String> updatePassword(@RequestBody String password) {
        return ResponseEntity.ok();
    }
}