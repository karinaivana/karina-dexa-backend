package com.karinaDexaTest.employeespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder @Setter @Getter
public class GetAllEmployeeDataResponseDTO {
    private boolean isSuccess;
    private List<EmployeeDTO> employeeDTOList;
}
