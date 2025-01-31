package com.app.job_tracker.service;

import com.app.job_tracker.dto.UserUpdateDto;
import com.app.job_tracker.entity.User;
import com.app.job_tracker.exception.SessionExpiredException;
import com.app.job_tracker.exception.UserAlreadyExistsException;
import com.app.job_tracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByUsername(username)
                .orElseThrow(() -> new SessionExpiredException("session", "Session has expired. Please log in again."));
    }

    public User updateUser(UserUpdateDto userUpdateDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new SessionExpiredException("session", "Session has expired. Please log in again.");
        }

        User user = optionalUser.get();

        if (userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isEmpty()) {
            if (userRepo.findByEmail(userUpdateDto.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("email", "Email is already in use.");
            }
            user.setEmail(userUpdateDto.getEmail());
        }

        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        return userRepo.save(user);
    }

    public User deleteUser(){
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.deleteUserByUsername(userName)
                .orElseThrow(() -> new SessionExpiredException("session", "Session has expired. Please log in again."));
    }
}
