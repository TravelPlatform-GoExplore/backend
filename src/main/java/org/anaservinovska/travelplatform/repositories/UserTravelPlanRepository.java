package org.anaservinovska.travelplatform.repositories;

import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.anaservinovska.travelplatform.models.UserTravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTravelPlanRepository extends JpaRepository<UserTravelPlan, Long> {
    List<UserTravelPlan> findAllByUser_Username(String username);
}
