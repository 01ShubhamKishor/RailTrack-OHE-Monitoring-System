package com.railtrack.railtrackohe.dto;


import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DashboardDTO {
    private long totalRaised;
    private long totalAssigned;
    private long totalResolved;
    private long totalClosed;
    private long totalRejected;
    
    private List<Map<String, Object>> defectsByType; // Pie chart
    private List<Map<String, Object>> defectsByStation; // Bar chart
    private List<Map<String, Object>> defectsByStatus; // Status wise
}