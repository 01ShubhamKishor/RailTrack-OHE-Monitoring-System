package com.railtrack.railtrackohe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.railtrack.railtrackohe.entity.Station;
import com.railtrack.railtrackohe.repository.StationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository repo;

    public Station save(Station s){ return repo.save(s); }
    public List<Station> getAll(){ return repo.findAll(); }
    public Station getById(Long id){ return repo.findById(id).orElseThrow(); }
    
    public List<Station> search(String q){
        return repo.search(q);
    }

    public Station update(Long id, Station newData){
        Station old = getById(id);
        old.setStationName(newData.getStationName());
        old.setStationCode(newData.getStationCode());
        old.setDivision(newData.getDivision());
        old.setZone(newData.getZone());
        return repo.save(old);
    }
    public void delete(Long id){ repo.deleteById(id); }
}