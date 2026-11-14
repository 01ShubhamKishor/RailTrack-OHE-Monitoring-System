package com.railtrack.railtrackohe.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*; 
import lombok.*;

@Entity
@Table(name = "power_blocks")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PowerBlock {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "defect_id")
    private Defect defect;

    @Enumerated(EnumType.STRING)
    private BlockStatus status;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private User requestedBy;

    private String blockSection; // ye field add kiya
    private String tpcRemark; // ye field add kiya

    @Column(name = "demand_from")
    private LocalDateTime demandFrom; // ye field add kiya

    @Column(name = "demand_to")
    private LocalDateTime demandTo; // ye field add kiya

    @Column(name = "granted_at")
    private LocalDateTime grantedAt;
    
    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum BlockStatus { REQUESTED, GRANTED, RESTORED, REJECTED }
}