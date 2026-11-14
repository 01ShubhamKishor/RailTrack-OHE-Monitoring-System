package com.railtrack.railtrackohe.controller;

import com.railtrack.railtrackohe.entity.PowerBlock;
import com.railtrack.railtrackohe.service.PowerBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/power-blocks")
@RequiredArgsConstructor
public class PowerBlockController {

    private final PowerBlockService powerBlockService;

    @PostMapping
    public PowerBlock request(@RequestBody PowerBlock req){
        return powerBlockService.requestBlock(req);
    }

    @PutMapping("/{id}/status")
    public PowerBlock updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam String remark){
        return powerBlockService.updateStatus(id, status, remark);
    }

    @GetMapping
    public List<PowerBlock> getAll(){
        return powerBlockService.getAll();
    }

    @GetMapping("/defect/{defectId}")
    public List<PowerBlock> getByDefect(@PathVariable Long defectId){
        return powerBlockService.getByDefectId(defectId);
    }
}