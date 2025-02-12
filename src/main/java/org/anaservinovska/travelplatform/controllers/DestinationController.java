package org.anaservinovska.travelplatform.controllers;

import org.anaservinovska.travelplatform.models.Destination;
import org.anaservinovska.travelplatform.services.DestinationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAll();
    }
}
