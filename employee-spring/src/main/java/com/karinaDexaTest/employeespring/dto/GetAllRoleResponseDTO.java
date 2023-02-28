package com.karinaDexaTest.employeespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder @Setter @Getter
public class GetAllRoleResponseDTO {
    private boolean isSuccess;
    List<RoleDTO> roleDTOS;
}
