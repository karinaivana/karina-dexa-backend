package com.karinaDexaTest.employeespring.dto;

import com.karinaDexaTest.employeespring.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
public class EmployeeDTO {
    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private String name;
    private String email;
    private String password;
    private RoleDTO roleDTO;
    private String phoneNumber;
    private String photoLink;
}
