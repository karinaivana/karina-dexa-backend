package com.karinaDexaTest.employeespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UpdateEmployeePersonalDataResponseDTO {
    private boolean isSuccess;
    private String message;
    private EmployeeDTO employeeDTO;
}
