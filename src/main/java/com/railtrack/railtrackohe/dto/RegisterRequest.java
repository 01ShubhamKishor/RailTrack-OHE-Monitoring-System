package com.railtrack.railtrackohe.dto;

import com.railtrack.railtrackohe.entity.Role;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String department;
    private String phone;
    private String employeeId;
    
    private Role role;
}