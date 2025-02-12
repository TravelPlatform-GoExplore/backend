package org.anaservinovska.travelplatform.controllers;

import org.anaservinovska.travelplatform.models.Destination;
import org.anaservinovska.travelplatform.models.POI;
import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.anaservinovska.travelplatform.models.UserTravelPlan;
import org.anaservinovska.travelplatform.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/travel-plan")
public class TravelPlanController {
    private final UserTravelPlanService userTravelPlanService;
    private final UserService userService;
    private final DestinationService destinationService;
    private final POIService poiService;

    public TravelPlanController(UserTravelPlanService service, UserService userService, DestinationService destinationService, POIService poiService) {
        this.userTravelPlanService = service;
        this.userService = userService;
        this.destinationService = destinationService;
        this.poiService = poiService;
    }

    @GetMapping
    public List<UserTravelPlan> getAllPlans(@AuthenticationPrincipal UserDetails userDetails) {
        return userTravelPlanService.getTravelPlansForUser(userDetails.getUsername());
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserTravelPlan savePlan(@RequestBody Map<String, Object> planRequest, @AuthenticationPrincipal UserDetails userDetails) {
        TravelPlatformUser user = userService.findByUsername(userDetails.getUsername());
        Integer dest_id = (Integer) planRequest.get("destination_id");
        Destination destination = destinationService.findById(dest_id.longValue());
        List<?> poiIdsRaw = (List<?>) planRequest.get("pois"); // Get raw list
        List<Long> poiIdsLong = poiIdsRaw.stream()
                .map(o -> ((Number) o).longValue()) // Convert to Long safely
                .collect(Collectors.toList());


        List<POI> poisRaw = poiService.findAllById(poiIdsLong);
        if(user == null || destination == null || poisRaw.isEmpty()) {
            return null;
        }
        UserTravelPlan plan = new UserTravelPlan();

        // [destination, tripTypes, startDate, endDate, pois]
        System.out.println(planRequest.get("tripTypes"));
        plan.setUser(user);
        List<String> tripTypesRaw = (List<String>) planRequest.get("tripTypes");
        Set<String> tripTypes = new HashSet<>(tripTypesRaw);
        plan.setTripTypes(tripTypes);
        plan.setDestination(destination);
        plan.setStartDate(LocalDate.parse((String) planRequest.get("startDate")));
        plan.setEndDate(LocalDate.parse((String) planRequest.get("endDate")));
        Set<POI> pois = new HashSet<>(poisRaw);
        plan.setPois(pois);
        return userTravelPlanService.saveTravelPlan(plan);
    }
}
