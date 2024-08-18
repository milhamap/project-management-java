package com.example.training_intern.modules.Project.model;

import com.example.training_intern.modules.Locations.model.Location;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String client;
    @Temporal(TemporalType.DATE)
    @Column
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column
    private Date endDate;
    @Column
    private String chairPerson;
    @Column
    private String description;
    @ManyToMany
    @JoinTable(
        name = "project_locations",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Set<Location> locations = new HashSet<>();
    @Column
    private Date createdAt;
    @Column
    private Date updatedAt;
}
