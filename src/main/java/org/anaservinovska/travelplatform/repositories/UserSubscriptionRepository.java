package org.anaservinovska.travelplatform.repositories;

import org.anaservinovska.travelplatform.models.UserSubscription;
import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    @Query("SELECT us FROM UserSubscription us WHERE us.user = ?1 AND us.isActive = true AND CURRENT_TIMESTAMP BETWEEN us.startDate AND COALESCE(us.endDate, '2099-12-31 11:59:59')")
    Optional<UserSubscription> findActiveSubscriptionForUser(TravelPlatformUser user);

    Optional<UserSubscription> findByUserAndIsActiveTrue(TravelPlatformUser user);
}
