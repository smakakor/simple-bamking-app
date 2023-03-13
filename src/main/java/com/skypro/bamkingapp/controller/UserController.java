package com.skypro.bamkingapp.controller;

import com.skypro.bamkingapp.dto.UserDTO;
import com.skypro.bamkingapp.dto.request.ChangePasswordRequest;
import com.skypro.bamkingapp.dto.request.CreateUserRequest;
import com.skypro.bamkingapp.service.AccountService;
import com.skypro.bamkingapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<UserDTO> getAll() {
        return this.userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserRequest userRequest) {
        return this.userService.addUser(userRequest);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        this.userService.updatePassword("", changePasswordRequest.oldPassword(),
                changePasswordRequest.newPassword());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        this.userService.removeUser(username);
        return ResponseEntity.noContent().build();
    }
}
