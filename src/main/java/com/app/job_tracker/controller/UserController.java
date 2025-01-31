package com.app.job_tracker.controller;

import com.app.job_tracker.dto.UserUpdateDto;
import com.app.job_tracker.entity.User;
import com.app.job_tracker.repository.UserRepo;
import com.app.job_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public User getUserDetails(){
        return userService.getUserDetails();
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        User updatedUser = userService.updateUser(userUpdateDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
