package com.karinaDexaTest.employeespring.service;

import com.karinaDexaTest.employeespring.dto.*;

public interface EmployeeService {
    LoginResponseDTO login(LoginRequestDTO dto);
    UpdateEmployeePersonalDataResponseDTO updateEmployeePersonalData(UpdateEmployeePersonalDataRequestDTO dto);
    boolean validateEmployeeById(long employeeId);
    CreateNewEmployeeResponseDTO createNewEmployee(CreateNewEmployeeRequestDTO dto);
}
