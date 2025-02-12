package org.anaservinovska.travelplatform.services;

import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.anaservinovska.travelplatform.models.UserTravelPlan;
import org.anaservinovska.travelplatform.repositories.UserTravelPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTravelPlanService {
    private final UserTravelPlanRepository repository;

    public UserTravelPlanService(UserTravelPlanRepository repository) {
        this.repository = repository;
    }

    public UserTravelPlan saveTravelPlan(UserTravelPlan plan) {
        return this.repository.save(plan);
    }

    public List<UserTravelPlan> getTravelPlansForUser(String username) {
        return this.repository.findAllByUser_Username(username);
    }
}
