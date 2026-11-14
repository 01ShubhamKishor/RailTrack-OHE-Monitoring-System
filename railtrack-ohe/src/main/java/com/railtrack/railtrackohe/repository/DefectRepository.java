package com.railtrack.railtrackohe.repository;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.railtrack.railtrackohe.entity.Defect;
import com.railtrack.railtrackohe.entity.Defect.DefectStatus;

public interface DefectRepository extends JpaRepository<Defect, Long> {
    
    List<Defect> findByStatus(Defect.DefectStatus status);
    
    List<Defect> findByNearestStation_Id(Long stationId);
    
    List<Defect> findByRaisedBy_Id(Long userId);
    
    List<Defect> findByAssignedTo_Id(Long userId);
    
    
    //Dashboard
    
    long countByStatus(DefectStatus status);

    // Type wise
    @Query("SELECT dt.name as name, COUNT(d) as count FROM Defect d JOIN d.defectType dt GROUP BY dt.name")
    List<Map<String, Object>> countGroupByDefectType();

    //Station wise
    @Query("SELECT s.name as name, COUNT(d) as count FROM Defect d JOIN d.nearestStation s GROUP BY s.name")
    List<Map<String, Object>> countGroupByStation();

    //Status wise
    @Query("SELECT d.status as name, COUNT(d) as count FROM Defect d GROUP BY d.status")
    List<Map<String, Object>> countGroupByStatus();

    // for Maintenance dashboard
    long countByAssignedToIdAndStatus(Long userId, DefectStatus status);
    long countByAssignedToId(Long userId);
}