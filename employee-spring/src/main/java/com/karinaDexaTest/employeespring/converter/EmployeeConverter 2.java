package com.karinaDexaTest.employeespring.converter;

import com.karinaDexaTest.employeespring.dto.EmployeeDTO;
import com.karinaDexaTest.employeespring.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {
    private final ModelMapper modelMapper;

    public EmployeeConverter(
            ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO toDTO(Employee employee) {
        if(employee == null) return null;

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        return employeeDTO;
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {
        if(employeeDTO == null) return null;

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        return employee;
    }
}
