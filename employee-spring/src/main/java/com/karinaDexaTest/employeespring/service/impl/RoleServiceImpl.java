package com.karinaDexaTest.employeespring.service.impl;

import com.karinaDexaTest.employeespring.converter.RoleConverter;
import com.karinaDexaTest.employeespring.dto.GetAllEmployeeDataResponseDTO;
import com.karinaDexaTest.employeespring.dto.GetAllRoleResponseDTO;
import com.karinaDexaTest.employeespring.dto.RoleDTO;
import com.karinaDexaTest.employeespring.model.Employee;
import com.karinaDexaTest.employeespring.model.Role;
import com.karinaDexaTest.employeespring.repository.RoleRepository;
import com.karinaDexaTest.employeespring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Autowired
    public RoleServiceImpl(
            RoleRepository roleRepository,
            RoleConverter roleConverter
    ){
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    private final int MAX_TOTAL_ROLE_SHOW = 1000;

    @Override
    public GetAllRoleResponseDTO getAllRole() {
        Page<Role> rolePage = roleRepository.getAllRoleNotDeleted(Pageable.ofSize(MAX_TOTAL_ROLE_SHOW));

        List<RoleDTO> roleDTOS = roleConverter.convertToDtoList(rolePage);

        return GetAllRoleResponseDTO.builder()
                .isSuccess(true)
                .roleDTOS(roleDTOS)
                .build();
    }
}
