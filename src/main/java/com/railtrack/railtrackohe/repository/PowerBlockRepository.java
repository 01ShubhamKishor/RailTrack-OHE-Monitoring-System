package com.railtrack.railtrackohe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railtrack.railtrackohe.entity.PowerBlock;

public interface PowerBlockRepository extends JpaRepository<PowerBlock, Long> {
    List<PowerBlock> findByDefect_Id(Long defectId);
}