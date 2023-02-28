package com.karinaDexaTest.employeespring.controller;

import com.karinaDexaTest.employeespring.dto.GetAllRoleResponseDTO;
import com.karinaDexaTest.employeespring.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(
            RoleService roleService
    ) {
        this.roleService = roleService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<GetAllRoleResponseDTO> getAllRoleList() {
        GetAllRoleResponseDTO getAllRoleResponseDTO = roleService.getAllRole();

        return ResponseEntity.ok(getAllRoleResponseDTO);
    }
}
