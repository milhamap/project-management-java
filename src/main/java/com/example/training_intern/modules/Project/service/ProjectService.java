package com.example.training_intern.modules.Project.service;

import com.example.training_intern.modules.Project.dto.CreateProjectDto;
import com.example.training_intern.modules.Project.dto.UpdateProjectDto;
import com.example.training_intern.modules.Project.model.Project;
import utils.Result;

import java.util.List;

public interface ProjectService {
    Result<List<Project>> findAll(Long limit, Long offset);
    Project findProjectById(Long id);
    Project createProject(CreateProjectDto createProjectDto) throws Exception;
    Project updateProjectById(Long id, UpdateProjectDto project) throws Exception;
    void deleteProjectById(Long id);
}
