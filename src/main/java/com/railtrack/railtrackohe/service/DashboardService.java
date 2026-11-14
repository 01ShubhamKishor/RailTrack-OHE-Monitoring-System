package com.railtrack.railtrackohe.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.railtrack.railtrackohe.dto.DashboardDTO;
import com.railtrack.railtrackohe.entity.Defect.DefectStatus;
import com.railtrack.railtrackohe.repository.DefectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DefectRepository defectRepository;

    public DashboardDTO getControlDashboard() {
        DashboardDTO dto = new DashboardDTO();
        dto.setTotalRaised(defectRepository.countByStatus(DefectStatus.RAISED));
        dto.setTotalAssigned(defectRepository.countByStatus(DefectStatus.ASSIGNED));
        dto.setTotalResolved(defectRepository.countByStatus(DefectStatus.RESOLVED));
        dto.setTotalClosed(defectRepository.countByStatus(DefectStatus.CLOSED));
        dto.setTotalRejected(defectRepository.countByStatus(DefectStatus.REJECTED));

        dto.setDefectsByType(defectRepository.countGroupByDefectType());
        dto.setDefectsByStation(defectRepository.countGroupByStation());
        dto.setDefectsByStatus(defectRepository.countGroupByStatus());
        return dto;
    }

    public Map<String, Object> getMaintenanceDashboard(Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("myPending", defectRepository.countByAssignedToIdAndStatus(userId, DefectStatus.ASSIGNED));
        map.put("myResolvedToday", defectRepository.countByAssignedToIdAndStatus(userId, DefectStatus.RESOLVED));
        map.put("myTotalAssigned", defectRepository.countByAssignedToId(userId));
        return map;
    }
}