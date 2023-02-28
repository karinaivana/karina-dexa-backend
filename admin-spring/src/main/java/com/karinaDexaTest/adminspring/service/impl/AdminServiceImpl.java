package com.karinaDexaTest.adminspring.service.impl;

import com.karinaDexaTest.adminspring.converter.AdminConverter;
import com.karinaDexaTest.adminspring.dto.CreateNewAdminRequestDTO;
import com.karinaDexaTest.adminspring.dto.CreateNewAdminResponseDTO;
import com.karinaDexaTest.adminspring.dto.LoginRequestDTO;
import com.karinaDexaTest.adminspring.dto.LoginResponseDTO;
import com.karinaDexaTest.adminspring.model.Admin;
import com.karinaDexaTest.adminspring.repository.AdminRepository;
import com.karinaDexaTest.adminspring.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminConverter adminConverter;
    private final AdminRepository adminRepository;

    public AdminServiceImpl(
            AdminConverter adminConverter,
            AdminRepository adminRepository
    ) {
        this.adminConverter = adminConverter;
        this.adminRepository = adminRepository;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        if(dto.getUsername() == null || dto.getUsername().isEmpty() || dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return LoginResponseDTO.builder()
                    .isSuccess(false)
                    .message("Admin login has failed because the email or password is null.")
                    .build();
        }

        Admin admin = adminRepository.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());

        if(admin == null) {
            return LoginResponseDTO.builder()
                    .isSuccess(false)
                    .message("Admin login has failed because data doesn't exist")
                    .build();
        }

        return LoginResponseDTO.builder()
                .isSuccess(true)
                .message("Admin login was succeed")
                .adminDTO(adminConverter.toDTO(admin))
                .build();
    }

    @Override
    @Transactional
    public CreateNewAdminResponseDTO createNewAdmin(CreateNewAdminRequestDTO dto) {
        if(dto.getUsername() == null || dto.getUsername().isEmpty() || dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return CreateNewAdminResponseDTO.builder()
                    .isSuccess(false)
                    .message("Creating new admin has failed because the phone number is not valid")
                    .build();
        }

        Admin existingAdmin = adminRepository.findExistingAdminByUserName(dto.getUsername());

        if(existingAdmin != null) {
            return CreateNewAdminResponseDTO.builder()
                    .isSuccess(false)
                    .message("Creating new admin has failed because the username already exist")
                    .build();
        }

        Admin newAdmin = new Admin();

        newAdmin.setUsername(dto.getUsername());
        newAdmin.setPassword(dto.getPassword());
        adminRepository.save(newAdmin);

        return CreateNewAdminResponseDTO.builder()
                .isSuccess(true)
                .message("A new admin has been successfully created.")
                .adminDTO(adminConverter.toDTO(newAdmin))
                .build();
    }
}
