package org.anaservinovska.travelplatform.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_travel_plan")
public class UserTravelPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Prevents serialization of this side
    private TravelPlatformUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    private Set<String> tripTypes;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_travel_plan_pois", // Join table name
            joinColumns = @JoinColumn(name = "user_travel_plan_id"), // Column linking to UserTravelPlan
            inverseJoinColumns = @JoinColumn(name = "poi_id") // Column linking to Destination
    )
    private Set<POI> pois;
}
