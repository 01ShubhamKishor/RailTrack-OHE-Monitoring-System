package com.railtrack.railtrackohe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.railtrack.railtrackohe.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
	
    
    @Query("SELECT s FROM Station s WHERE " +
            "LOWER(s.stationCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.stationName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.division) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.zone) LIKE LOWER(CONCAT('%', :search, '%'))")
     List<Station> search(@Param("search") String search);
}