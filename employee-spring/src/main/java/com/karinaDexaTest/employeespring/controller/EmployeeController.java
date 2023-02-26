package com.karinaDexaTest.employeespring.controller;

import com.karinaDexaTest.employeespring.dto.*;
import com.karinaDexaTest.employeespring.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService
    ) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginEmployee(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        LoginResponseDTO loginResponseDTO = employeeService.login(loginRequestDTO);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateEmployeePersonalDataResponseDTO> updateEmployeePersonalData(
            @RequestBody UpdateEmployeePersonalDataRequestDTO updateEmployeePersonalDataRequestDTO
    ) {
        UpdateEmployeePersonalDataResponseDTO updateEmployeePersonalDataResponseDTO = employeeService.updateEmployeePersonalData(updateEmployeePersonalDataRequestDTO);

        return ResponseEntity.ok(updateEmployeePersonalDataResponseDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateNewEmployeeResponseDTO> createNewEmployee(
            @RequestBody CreateNewEmployeeRequestDTO createNewEmployeeRequestDTO
    ) {
        CreateNewEmployeeResponseDTO createNewEmployeeResponseDTO = employeeService.createNewEmployee(createNewEmployeeRequestDTO);

        return ResponseEntity.ok(createNewEmployeeResponseDTO);
    }

    @GetMapping("validate/{employeeId}")
    public ResponseEntity<Boolean> validateEmployeeById(
            @PathVariable("employeeId") long employeeId
    ) {
        return ResponseEntity.ok(employeeService.validateEmployeeById(employeeId));
    }


}
