package org.anaservinovska.travelplatform.repositories;

import jakarta.persistence.TypedQuery;
import org.anaservinovska.travelplatform.models.POI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface POIRepository extends JpaRepository<POI, Long> {
    List<POI> findAllByIdIn(List<Long> ids);
}
