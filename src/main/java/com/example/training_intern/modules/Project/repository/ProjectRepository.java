package com.example.training_intern.modules.Project.repository;

import com.example.training_intern.modules.Project.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p")
    @EntityGraph(attributePaths = {"locations"})
    List<Project> findAll();
}
