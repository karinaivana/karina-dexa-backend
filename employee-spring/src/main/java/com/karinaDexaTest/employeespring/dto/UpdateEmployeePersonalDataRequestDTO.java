package com.karinaDexaTest.employeespring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateEmployeePersonalDataRequestDTO {
    private long employeeId;
    private String photoLink;
    private String phoneNumber;
    private String password;
}
