package com.railtrack.railtrackohe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railtrack.railtrackohe.entity.Role;
import com.railtrack.railtrackohe.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmployeeId(String employeeId);
    List<User> findByRole(Role role);
}