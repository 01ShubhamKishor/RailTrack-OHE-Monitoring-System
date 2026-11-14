package com.railtrack.railtrackohe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railtrack.railtrackohe.entity.Station;
import com.railtrack.railtrackohe.service.StationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationController {
    
    private final StationService service;

    @PostMapping
    public Station create(@RequestBody Station station){
        return service.save(station);
    }

    @GetMapping
    public List<Station> getAll(@RequestParam(required = false) String search){
        if(search != null && !search.trim().isEmpty()) {
            return service.search(search); // if search is available then use search
        }
        return service.getAll(); // else give all 
    }
    @GetMapping("/{id}")
    public Station getOne(@PathVariable Long id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Station update(@PathVariable Long id, @RequestBody Station s){
        return service.update(id, s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}