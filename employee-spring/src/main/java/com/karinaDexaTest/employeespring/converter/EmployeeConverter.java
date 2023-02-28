package com.karinaDexaTest.employeespring.converter;

import com.karinaDexaTest.employeespring.dto.EmployeeDTO;
import com.karinaDexaTest.employeespring.dto.RoleDTO;
import com.karinaDexaTest.employeespring.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeConverter {
    private final ModelMapper modelMapper;
    private final RoleConverter roleConverter;

    public EmployeeConverter(
            ModelMapper modelMapper,
            RoleConverter roleConverter
    ) {
        this.modelMapper = modelMapper;
        this.roleConverter = roleConverter;
    }

    public EmployeeDTO toDTO(Employee employee) {
        if(employee == null) return null;

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        RoleDTO roleDTO = roleConverter.toDTO(employee.getRole());
        employeeDTO.setRoleDTO(roleDTO);

        return employeeDTO;
    }

    public List<EmployeeDTO> convertToDtoList(Page<Employee> employeePage) {
        List<Employee> entities = employeePage.getContent();
        return entities.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
    }

    public Employee toEntity(EmployeeDTO employeeDTO) {
        if(employeeDTO == null) return null;

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        return employee;
    }
}
