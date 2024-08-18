package com.example.training_intern.modules.Locations.model;

import com.example.training_intern.modules.Project.model.Project;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String province;
    @Column
    private String city;
    @ManyToMany(mappedBy = "locations")
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();
    @Column
    private Date createdAt;
    @Column
    private Date updatedAt;
}
