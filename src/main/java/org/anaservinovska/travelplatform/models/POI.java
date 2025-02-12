package org.anaservinovska.travelplatform.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class POI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    @JsonBackReference // Prevents recursion by ignoring this reference in serialization (POI -> Destination)
    private Destination destination;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private Double lat;
    @Column(nullable = false)
    private Double lng;



}
