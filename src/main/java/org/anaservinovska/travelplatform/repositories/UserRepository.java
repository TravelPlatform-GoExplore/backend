package org.anaservinovska.travelplatform.repositories;

import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<TravelPlatformUser, Long> {
    Optional<TravelPlatformUser> findByUsername(String username);
}
