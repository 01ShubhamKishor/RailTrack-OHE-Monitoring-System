package com.railtrack.railtrackohe.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railtrack.railtrackohe.entity.Role;
import com.railtrack.railtrackohe.entity.User;
import com.railtrack.railtrackohe.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

        @GetMapping("/me")
        public ResponseEntity<?> me(Authentication auth){
            User user = (User) auth.getPrincipal();
            return ResponseEntity.ok(user);
        }
       
    @GetMapping
    public ResponseEntity<?> getByRole(@RequestParam String role){
    	   // String -> Enum convert
        Role enumRole = Role.valueOf(role.toUpperCase()); // "maintenance" -> MAINTENANCE
        return ResponseEntity.ok(userService.findByRole(enumRole));
     }
    
    @GetMapping("/api/v1/users")
    public List<User> getAll()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id)
    {
        return userService.getById(id);
    }
}
//All are working fine