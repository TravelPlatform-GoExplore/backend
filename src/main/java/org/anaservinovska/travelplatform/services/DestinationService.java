package org.anaservinovska.travelplatform.services;

import org.anaservinovska.travelplatform.models.Destination;
import org.anaservinovska.travelplatform.repositories.DestinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAll() {
        return destinationRepository.findAll();
    }
    public Destination findById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }
}
