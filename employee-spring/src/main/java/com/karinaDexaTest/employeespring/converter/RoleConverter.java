package com.karinaDexaTest.employeespring.converter;

import com.karinaDexaTest.employeespring.dto.EmployeeDTO;
import com.karinaDexaTest.employeespring.dto.RoleDTO;
import com.karinaDexaTest.employeespring.model.Employee;
import com.karinaDexaTest.employeespring.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter {
    private final ModelMapper modelMapper;

    public RoleConverter(
            ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    public RoleDTO toDTO(Role role) {
        if(role == null) return null;

        RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);

        return roleDTO;
    }

    public Role toEntity(RoleDTO roleDTO) {
        if(roleDTO == null) return null;

        Role role = modelMapper.map(roleDTO, Role.class);

        return role;
    }

    public List<RoleDTO> convertToDtoList(Page<Role> rolePage) {
        List<Role> entities = rolePage.getContent();
        return entities.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
    }
}
