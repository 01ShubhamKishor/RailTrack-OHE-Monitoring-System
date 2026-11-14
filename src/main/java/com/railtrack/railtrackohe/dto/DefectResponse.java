package com.railtrack.railtrackohe.dto;



import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder 
@NoArgsConstructor 
@AllArgsConstructor
public class DefectResponse {
    private Long id;
    private String defectNo;        // OHE-123456
    private String mastNo;
    private String kmNumber;
    private String section;
    private String nearestStationName; // DDU ka naam, id nahi
    private String defectTypeName;     // OHE Sag
    private String description;
    private String status;          // RAISED
    private String priority;        // MEDIUM
    private Double latitude;
    private Double longitude;
    private LocalDateTime raisedAt;
    private List<String> photoUrls; // saari photos ki list
}