package com.railtrack.railtrackohe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.railtrack.railtrackohe.entity.DefectPhoto;

public interface DefectPhotoRepository extends JpaRepository<DefectPhoto, Long> {
    List<DefectPhoto> findByDefect_Id(Long defectId);
}