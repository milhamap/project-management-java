package com.example.training_intern.modules.Project.controller;

import com.example.training_intern.modules.Project.dto.CreateProjectDto;
import com.example.training_intern.modules.Project.dto.UpdateProjectDto;
import com.example.training_intern.modules.Project.model.Project;
import com.example.training_intern.modules.Project.service.ProjectService;
import helpers.Response;
import helpers.ResponsePagination;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.Result;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<ResponsePagination<List<Project>>> findAllProject(
            @RequestParam(defaultValue = "0") Long start,
            @RequestParam(defaultValue = "10") Long quantity) {

        Result<List<Project>> result = projectService.findAll(quantity, start);

        ResponsePagination<List<Project>> response = new ResponsePagination<>(
                true, result.getData(), "List of projects", 200, result.getMeta()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Project>> findProjectById(@PathVariable("id") Long id) {
        Project project = projectService.findProjectById(id);
        Response<Project> response = new Response<>(
                true, project, "Project found", 200
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectDto projectCreateDto) {
        try {
            Project createdProject = projectService.createProject(projectCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Project>> updateProject(
            @PathVariable("id") Long id, @RequestBody UpdateProjectDto project) {
        try {
            Project updatedProject = projectService.updateProjectById(id, project);
            Response<Project> response = new Response<>(
                    true, updatedProject, "Project updated successfully", 200
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProjectById(id);
        Map<String, String> responseData = Map.of("message", "Project deleted successfully");

        Response<Object> response = new Response<>(
                true, responseData, "Project deleted", 200
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
