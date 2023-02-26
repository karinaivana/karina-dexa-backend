package com.karinaDexaTest.adminspring.controller;

import com.karinaDexaTest.adminspring.dto.CreateNewAdminRequestDTO;
import com.karinaDexaTest.adminspring.dto.CreateNewAdminResponseDTO;
import com.karinaDexaTest.adminspring.dto.LoginRequestDTO;
import com.karinaDexaTest.adminspring.dto.LoginResponseDTO;
import com.karinaDexaTest.adminspring.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(
            AdminService adminService
    ) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginEmployee(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        LoginResponseDTO loginResponseDTO = adminService.login(loginRequestDTO);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<CreateNewAdminResponseDTO> createNewAdmin(
            @RequestBody CreateNewAdminRequestDTO createNewAdminRequestDTO
    ) {
        CreateNewAdminResponseDTO createNewAdminResponseDTO = adminService.createNewAdmin(createNewAdminRequestDTO);

        return ResponseEntity.ok(createNewAdminResponseDTO);
    }
}
