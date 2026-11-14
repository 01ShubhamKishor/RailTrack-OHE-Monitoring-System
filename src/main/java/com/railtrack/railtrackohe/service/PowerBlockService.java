package com.railtrack.railtrackohe.service;


import com.railtrack.railtrackohe.entity.PowerBlock;
import com.railtrack.railtrackohe.repository.PowerBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerBlockService {

    private final PowerBlockRepository powerBlockRepo;

    // 1. Naya block demand
    public PowerBlock requestBlock(PowerBlock req){
        req.setStatus(PowerBlock.BlockStatus.REQUESTED);
        req.setCreatedAt(LocalDateTime.now());
        return powerBlockRepo.save(req);
    }

    // 2. TPC Approve / Reject / Restore
    public PowerBlock updateStatus(Long id, String status, String remark){
        PowerBlock pb = powerBlockRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PowerBlock not found with id: " + id));

        pb.setStatus(PowerBlock.BlockStatus.valueOf(status));
        pb.setTpcRemark(remark);

        if(status.equals("GRANTED")){
            pb.setGrantedAt(LocalDateTime.now());
        }
        if(status.equals("RESTORED")){
            pb.setRestoredAt(LocalDateTime.now());
        }

        return powerBlockRepo.save(pb);
    }

    // 3. Sab block list
    public List<PowerBlock> getAll(){
        return powerBlockRepo.findAll();
    }

    // 4. Ek defect ke sare block
    public List<PowerBlock> getByDefectId(Long defectId){
        return powerBlockRepo.findByDefect_Id(defectId);
    }
}