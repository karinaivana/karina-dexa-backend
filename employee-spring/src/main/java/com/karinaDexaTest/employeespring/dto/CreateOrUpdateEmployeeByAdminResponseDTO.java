package com.karinaDexaTest.employeespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Setter @Getter
public class CreateOrUpdateEmployeeByAdminResponseDTO {
    private boolean isSuccess;
    private String message;
    private EmployeeDTO employeeDTO;
}
