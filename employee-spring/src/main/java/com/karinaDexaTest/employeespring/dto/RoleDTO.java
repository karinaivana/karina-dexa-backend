package com.karinaDexaTest.employeespring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
public class RoleDTO {
    private String id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private String description;
}
