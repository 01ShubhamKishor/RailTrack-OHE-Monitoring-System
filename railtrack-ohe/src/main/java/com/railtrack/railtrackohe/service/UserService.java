package com.railtrack.railtrackohe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.railtrack.railtrackohe.entity.Role;
import com.railtrack.railtrackohe.entity.User;
import com.railtrack.railtrackohe.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User createUser(User user)
    {
        if(userRepo.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        if(userRepo.findByEmployeeId(user.getEmployeeId()).isPresent()){
            throw new RuntimeException("EmployeeId already exists: " + user.getEmployeeId());
        }
        return userRepo.save(user);
    }

    public List<User> getAllUsers()
    {
    	return userRepo.findAll(); 
    }

    public User getById(Long id)
    {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<User> findByRole(Role role) {
        return userRepo.findByRole(role);
    }

}