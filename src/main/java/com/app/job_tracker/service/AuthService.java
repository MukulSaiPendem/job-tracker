package com.app.job_tracker.service;

import com.app.job_tracker.entity.User;
import com.app.job_tracker.exception.IncorrectLoginDetailsException;
import com.app.job_tracker.exception.UserAlreadyExistsException;
import com.app.job_tracker.dto.LoginCreds;
import com.app.job_tracker.dto.UserDto;
import com.app.job_tracker.repository.UserRepo;
import com.app.job_tracker.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, Object> registerUser(UserDto userdto ){

        if (userRepo.findByUsername(userdto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("username", "Username is already taken.");
        }

        if (userRepo.findByEmail(userdto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("email", "Email is already in use.");
        }

        // Encode the password
        String encodedPass = passwordEncoder.encode(userdto.getPassword());
        userdto.setPassword(encodedPass);

        // Save the user with the encoded password
        User user = userRepo.save(User.fromDto(userdto));

        return createJWTforUser(user.getUsername());
    }

    public Map<String,Object> loginUser(@RequestBody LoginCreds body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
            authenticationManager.authenticate(authInputToken);
        }catch (AuthenticationException ex){
            throw new IncorrectLoginDetailsException("Login", "Incorrect Username/Password");
        }
        return createJWTforUser(body.getUsername());

    }

    public Map<String, Object> createJWTforUser(String username){
        String token = jwtUtil.generateToken(username);
        return Collections.singletonMap("jwt-token",token);
    }


}
