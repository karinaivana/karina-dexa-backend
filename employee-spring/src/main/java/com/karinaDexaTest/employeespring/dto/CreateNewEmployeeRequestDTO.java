package com.karinaDexaTest.employeespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateNewEmployeeRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String photoLink;
}
