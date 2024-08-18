package com.example.training_intern.modules.Project.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreateProjectDto {
    private String name;
    private String client;
    private String startDate;
    private String endDate;
    private String chairPerson;
    private String description;
    private Set<Long> locationIds;
}
