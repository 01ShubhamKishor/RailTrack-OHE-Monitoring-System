package com.railtrack.railtrackohe.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.railtrack.railtrackohe.dto.DefectActionRequest;
import com.railtrack.railtrackohe.dto.DefectCreateRequest;
import com.railtrack.railtrackohe.entity.Defect;
import com.railtrack.railtrackohe.service.DefectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/defects")
@RequiredArgsConstructor
public class DefectController {

    private final DefectService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Defect raise(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) throws IOException {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        DefectCreateRequest data = mapper.readValue(dataJson, DefectCreateRequest.class);
        return service.create(data, photos);
    }
  
    // GET /api/v1/defects?status=RAISED&stationId=1
    @GetMapping
    public List<Defect> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long stationId) {
        return service.getAll(status, stationId);
    }

    @GetMapping("/{id}")
    public Defect getOne(@PathVariable Long id){
        return service.getById(id);
    }

    @GetMapping("/my-raised")
    public List<Defect> myRaised(@RequestParam Long userId){
        return service.getByUserId(userId);
    }
    
    
    @PutMapping("/{id}/assign")
    public Defect assign(@PathVariable Long id, @RequestBody DefectActionRequest req){
        return service.assignDefect(id, req);
    }

    @PutMapping("/{id}/reject")
    public Defect reject(@PathVariable Long id, @RequestBody DefectActionRequest req){
        return service.rejectDefect(id, req);
    }

    @PutMapping("/{id}/resolve")
    public Defect resolve(@PathVariable Long id, @RequestBody DefectActionRequest req){
        return service.resolveDefect(id, req);
    }

    @PutMapping("/{id}/close")
    public Defect close(@PathVariable Long id, @RequestBody DefectActionRequest req){
        return service.closeDefect(id, req);
    }
}