package com.railtrack.railtrackohe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.railtrack.railtrackohe.dto.DashboardDTO;
import com.railtrack.railtrackohe.entity.User;
import com.railtrack.railtrackohe.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/control")
    @PreAuthorize("hasAnyRole('CONTROL','ADMIN')")
    public ResponseEntity<DashboardDTO> controlDashboard() {
        return ResponseEntity.ok(dashboardService.getControlDashboard());
    }

    @GetMapping("/maintenance")
    @PreAuthorize("hasRole('MAINTENANCE')")
    public ResponseEntity<?> maintenanceDashboard(Authentication authentication) {
        // Token se userId nikal
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(dashboardService.getMaintenanceDashboard(user.getId()));
    }
}