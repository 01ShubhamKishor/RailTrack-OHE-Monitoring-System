package com.railtrack.railtrackohe.dto;


import lombok.Data;

@Data
public class DefectCreateRequest {
    private Long defectTypeId;      // dropdown se ayega
    private Long nearestStationId;  // tere ER ka main point
    private String mastNo;
    private String kmNumber;
    private String section;         // DDU-GAYA
    private String description;
    private Double latitude;
    private Double longitude;
    private Long raisedById; 
    private String priority; // abhi manual, JWT ke baad hat jayega
}
