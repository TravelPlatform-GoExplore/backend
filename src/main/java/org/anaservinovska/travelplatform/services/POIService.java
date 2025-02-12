package org.anaservinovska.travelplatform.services;

import org.anaservinovska.travelplatform.models.POI;
import org.anaservinovska.travelplatform.repositories.POIRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POIService {
    private final POIRepository repository;

    public POIService(POIRepository repository) {
        this.repository = repository;
    }

    public List<POI> findAllById(List<Long> ids) {
        return repository.findAllByIdIn(ids);
    }
}
