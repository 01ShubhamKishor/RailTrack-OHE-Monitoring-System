package com.railtrack.railtrackohe.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity @Table(name = "defects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Defect {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "defect_no", unique = true)
    private String defectNo;
    
    @Column(name = "mast_no")
    private String mastNo;
    
    @Column(name = "km_number")
    private String kmNumber;
    
    private String section;

    @ManyToOne
    @JoinColumn(name = "nearest_station_id")
    private Station nearestStation;
    
    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @ManyToOne
    @JoinColumn(name = "defect_type_id")
    private DefectType defectType;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DefectStatus status = DefectStatus.RAISED;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @ManyToOne
    @JoinColumn(name = "raised_by")
    private User raisedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    @Column(name = "raised_at")
    @Builder.Default
    private LocalDateTime raisedAt = LocalDateTime.now();

    public enum DefectStatus { RAISED, ASSIGNED, RESOLVED, CLOSED, REJECTED }
    public enum Priority { HIGH, MEDIUM, LOW }
}