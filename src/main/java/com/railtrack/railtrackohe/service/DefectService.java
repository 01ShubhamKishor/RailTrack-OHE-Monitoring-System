package com.railtrack.railtrackohe.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.railtrack.railtrackohe.dto.DefectActionRequest;
import com.railtrack.railtrackohe.dto.DefectCreateRequest;
import com.railtrack.railtrackohe.entity.Defect;
import com.railtrack.railtrackohe.entity.DefectHistory;
import com.railtrack.railtrackohe.entity.DefectPhoto;
import com.railtrack.railtrackohe.entity.DefectType;
import com.railtrack.railtrackohe.entity.Station;
import com.railtrack.railtrackohe.entity.User;
import com.railtrack.railtrackohe.repository.DefectHistoryRepository;
import com.railtrack.railtrackohe.repository.DefectPhotoRepository;
import com.railtrack.railtrackohe.repository.DefectRepository;
import com.railtrack.railtrackohe.repository.DefectTypeRepository;
import com.railtrack.railtrackohe.repository.StationRepository;
import com.railtrack.railtrackohe.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class DefectService {
	
    private final DefectRepository defectRepo;
    private final StationRepository stationRepo;
    private final DefectTypeRepository typeRepo;
    private final  UserRepository userRepo;
    private final DefectPhotoRepository photoRepo;
    private  final DefectHistoryRepository historyRepo;

    // POST /api/v1/defects
    public Defect create(DefectCreateRequest req, List<MultipartFile> photos) throws IOException {
        Station station = stationRepo.findById(req.getNearestStationId())
                .orElseThrow(() -> new RuntimeException("Station ID " + req.getNearestStationId() + " DB me nahi hai"));
        DefectType type = typeRepo.findById(req.getDefectTypeId())
                .orElseThrow(() -> new RuntimeException("DefectType ID " + req.getDefectTypeId() + " DB me nahi hai"));
        User pilot = userRepo.findById(req.getRaisedById())
                .orElseThrow(() -> new RuntimeException("User ID " + req.getRaisedById() + " DB me nahi hai"));

        Defect defect = Defect.builder()
                .defectNo("OHE-" + System.currentTimeMillis())
                .mastNo(req.getMastNo())
                .kmNumber(req.getKmNumber())
                .section(req.getSection())
                .nearestStation(station)
                .defectType(type)
                .description(req.getDescription())
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .raisedBy(pilot)
                .status(Defect.DefectStatus.RAISED)
                .priority(req.getPriority() != null ? Defect.Priority.valueOf(req.getPriority()) : Defect.Priority.MEDIUM)
                .raisedAt(LocalDateTime.now())
                .build();
        Defect saved = defectRepo.save(defect);

        if(photos != null){
            for(MultipartFile file : photos){
                if(file.isEmpty()) continue;
                String url = saveFile(file);
                photoRepo.save(DefectPhoto.builder()
                        .defect(saved).photoUrl(url)
                        .photoType(DefectPhoto.PhotoType.RAISED)
                        .uploadedBy(pilot).build());
            }
        }
        historyRepo.save(DefectHistory.builder()
                .defect(saved).fromStatus(null).toStatus("RAISED")
                .actionBy(pilot).remark("Defect Raised at "+station.getStationName()).build());

        return saved;
    }

    private String saveFile(MultipartFile file) throws IOException {
        String folder = "uploads/";
        new File(folder).mkdirs();
        String name = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        file.transferTo(new File(folder+name));
        return "/uploads/"+name;
    }

    public List<Defect> getAll(String status, Long stationId){
        if(status != null) return defectRepo.findByStatus(Defect.DefectStatus.valueOf(status));
        if(stationId != null) return defectRepo.findByNearestStation_Id(stationId);
        return defectRepo.findAll();
    }
    
    public Defect getById(Long id){ return defectRepo.findById(id).orElseThrow(); }
    
    public List<Defect> getByUserId(Long userId){
        return defectRepo.findByRaisedBy_Id(userId);
    }
    
    
 // 1. ASSIGN (Control karega)
    public Defect assignDefect(Long defectId, DefectActionRequest req){
        Defect d = defectRepo.findById(defectId).orElseThrow();
        User actionBy = userRepo.findById(req.getActionById()).orElseThrow();
        User assignTo = userRepo.findById(req.getAssignedToId()).orElseThrow();

        String oldStatus = d.getStatus().name();
        d.setAssignedTo(assignTo);
        d.setStatus(Defect.DefectStatus.ASSIGNED);
        defectRepo.save(d);

        historyRepo.save(DefectHistory.builder()
                .defect(d).fromStatus(oldStatus).toStatus("ASSIGNED")
                .actionBy(actionBy).remark(req.getRemark()).createdAt(LocalDateTime.now()).build());
        return d;
    }

    // 2. REJECT (Maintenance reject karega)
    public Defect rejectDefect(Long defectId, DefectActionRequest req){
        Defect d = defectRepo.findById(defectId).orElseThrow();
        User actionBy = userRepo.findById(req.getActionById()).orElseThrow();
        
        String oldStatus = d.getStatus().name();
        d.setStatus(Defect.DefectStatus.REJECTED);
        defectRepo.save(d);

        historyRepo.save(DefectHistory.builder()
                .defect(d).fromStatus(oldStatus).toStatus("REJECTED")
                .actionBy(actionBy).remark(req.getRemark()).createdAt(LocalDateTime.now()).build());
        return d;
    }

    // 3. RESOLVE
    public Defect resolveDefect(Long defectId, DefectActionRequest req){
        Defect d = defectRepo.findById(defectId).orElseThrow();
        User actionBy = userRepo.findById(req.getActionById()).orElseThrow();
        
        String oldStatus = d.getStatus().name();
        d.setStatus(Defect.DefectStatus.RESOLVED);
        d.setResolvedAt(LocalDateTime.now());
        defectRepo.save(d);

        historyRepo.save(DefectHistory.builder()
                .defect(d).fromStatus(oldStatus).toStatus("RESOLVED")
                .actionBy(actionBy).remark(req.getRemark()).createdAt(LocalDateTime.now()).build());
        return d;
    }

    // 4. CLOSE (Control close karega)
    public Defect closeDefect(Long defectId, DefectActionRequest req){
        Defect d = defectRepo.findById(defectId).orElseThrow();
        User actionBy = userRepo.findById(req.getActionById()).orElseThrow();
        
        String oldStatus = d.getStatus().name();
        d.setStatus(Defect.DefectStatus.CLOSED);
        d.setClosedAt(LocalDateTime.now());
        defectRepo.save(d);

        historyRepo.save(DefectHistory.builder()
                .defect(d).fromStatus(oldStatus).toStatus("CLOSED")
                .actionBy(actionBy).remark(req.getRemark()).createdAt(LocalDateTime.now()).build());
        return d;
    }
}