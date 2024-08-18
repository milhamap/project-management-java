package com.example.training_intern.modules.Locations.repository;

import com.example.training_intern.modules.Locations.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
