package com.railtrack.railtrackohe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railtrack.railtrackohe.entity.DefectHistory;

public interface DefectHistoryRepository extends JpaRepository<DefectHistory, Long> {
    List<DefectHistory> findByDefect_IdOrderByCreatedAtAsc(Long defectId);
}
