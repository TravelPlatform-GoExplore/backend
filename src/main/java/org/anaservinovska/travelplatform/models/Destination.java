package org.anaservinovska.travelplatform.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer recommendedDays;

    @Column(nullable = false)
    private Double centerLat;

    @Column(nullable = false)
    private Double centerLng;

    @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER)
    @JsonManagedReference // This side is serialized (destination -> POI)
    private List<POI> listPOI = new ArrayList<>();
}
