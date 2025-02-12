package org.anaservinovska.travelplatform.repositories;

import org.anaservinovska.travelplatform.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
