package com.railtrack.railtrackohe.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "defect_history")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class DefectHistory {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "defect_id")
    private Defect defect;

    @Column(name = "from_status")
    private String fromStatus;
    @Column(name = "to_status")
    private String toStatus;

    @ManyToOne
    @JoinColumn(name = "action_by")
    private User actionBy;

    private String remark;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}