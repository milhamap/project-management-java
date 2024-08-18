package com.example.training_intern.modules.Project.service;

import com.example.training_intern.modules.Locations.model.Location;
import com.example.training_intern.modules.Locations.repository.LocationRepository;
import com.example.training_intern.modules.Project.dto.CreateProjectDto;
import com.example.training_intern.modules.Project.dto.UpdateProjectDto;
import com.example.training_intern.modules.Project.model.Project;
import com.example.training_intern.modules.Project.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private ProjectRepository projectRepository;
    private LocationRepository locationRepository;
    @Override
    public Result<List<Project>> findAll(Long limit, Long start) {
        Long countData = projectRepository.count();

        if (countData == 0) {
            return new Result<>(List.of());
        }

        List<Project> resultQuery = projectRepository.findAll().stream()
                .skip(start)
                .limit(limit)
                .toList();


        int totalPage = (int) Math.ceil((double) countData / limit);

        Map<String, Object> meta = new HashMap<>();
        meta.put("page", (start/10) + 1);
        meta.put("quantity", resultQuery.size());
        meta.put("totalPage", totalPage);
        meta.put("totalData", countData);

        return new Result<>(resultQuery, meta);
    }

    @Override
    public Project findProjectById(Long id) {
        Project project = projectRepository.findById(id).get();
        Hibernate.initialize(project.getLocations());
        return project;
    }

    @Override
    public Project createProject(CreateProjectDto createProjectDto) throws Exception {
        Project project = new Project();
        project.setName(createProjectDto.getName());
        project.setClient(createProjectDto.getClient());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = dateFormat.parse(createProjectDto.getStartDate());
        Date endDate = dateFormat.parse(createProjectDto.getEndDate());

        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setChairPerson(createProjectDto.getChairPerson());
        project.setDescription(createProjectDto.getDescription());
        project.setCreatedAt(new Date());

        Set<Location> locations = new HashSet<>();
        System.out.println(createProjectDto.getLocationIds());
        for (Long locationId : createProjectDto.getLocationIds()) {
            Location location = locationRepository.findById(locationId)
                    .orElseThrow(() -> new Exception("Location not found with ID: " + locationId));
            locations.add(location);
        }

        project.setLocations(locations);

        return projectRepository.save(project);
    }

    @Override
    public Project updateProjectById(Long id, UpdateProjectDto project) throws Exception {
        Project existingProject = projectRepository.findById(id).get();
        if (project.getName() != null) {
            existingProject.setName(project.getName());
        }
        if (project.getClient() != null) {
            existingProject.setClient(project.getClient());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (project.getStartDate() != null) {
            Date startDate = dateFormat.parse(project.getStartDate());
            existingProject.setStartDate(startDate);
        }

        if (project.getEndDate() != null) {
            Date endDate = dateFormat.parse(project.getEndDate());
            existingProject.setEndDate(endDate);
        }

        if (project.getChairPerson() != null) {
            existingProject.setChairPerson(project.getChairPerson());
        }

        if (project.getDescription() != null) {
            existingProject.setDescription(project.getDescription());
        }
        existingProject.setUpdatedAt(new Date());

        if (project.getNewLocationIds() != null) {
            Set<Location> locations = new HashSet<>();
            for (Long locationId : project.getNewLocationIds()) {
                Location location = locationRepository.findById(locationId)
                        .orElseThrow(() -> new Exception("Location not found with ID: " + locationId));
                locations.add(location);
            }

            existingProject.setLocations(locations);
        } else {
            existingProject.getLocations().clear();
        }

        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }
}
