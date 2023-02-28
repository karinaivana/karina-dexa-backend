package com.karinaDexaTest.employeespring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrUpdateEmployeeByAdminRequestDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String photoLink;
}
