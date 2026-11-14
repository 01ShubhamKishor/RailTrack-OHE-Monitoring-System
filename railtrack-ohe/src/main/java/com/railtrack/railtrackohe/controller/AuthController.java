package com.railtrack.railtrackohe.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.railtrack.railtrackohe.dto.LoginRequest;
import com.railtrack.railtrackohe.dto.RegisterRequest;
import com.railtrack.railtrackohe.entity.User;
import com.railtrack.railtrackohe.repository.UserRepository;
import com.railtrack.railtrackohe.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
          
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

        
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            
            String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());


            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", user.getRole(),
                    "email", user.getEmail(),
                    "name", user.getName(),
                    "department", user.getDepartment()
                ));

        } catch (Exception e) 
        {
            return ResponseEntity.status(401).body(Map.of("error", "Email ya Password galat hai"));
        }
        
     
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        
        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Map.of("error","Email already exists"));
        }

        User newUser = new User();
        newUser.setName(req.getName());
        newUser.setEmail(req.getEmail());
        newUser.setPhone(req.getPhone());
        newUser.setEmployeeId(req.getEmployeeId());
        newUser.setPassword(passwordEncoder.encode(req.getPassword())); 
        newUser.setDepartment(req.getDepartment());
        newUser.setRole(req.getRole());
        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message","User registered successfully"));
    }
}