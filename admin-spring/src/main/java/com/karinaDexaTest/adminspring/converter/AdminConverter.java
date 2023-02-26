package com.karinaDexaTest.adminspring.converter;

import com.karinaDexaTest.adminspring.dto.AdminDTO;
import com.karinaDexaTest.adminspring.model.Admin;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter {
    private final ModelMapper modelMapper;

    public AdminConverter(
            ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    public AdminDTO toDTO(Admin admin) {
        if(admin == null) return null;

        AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);

        return adminDTO;
    }

    public Admin toEntity(AdminDTO adminDTO) {
        if(adminDTO == null) return null;

        Admin admin = modelMapper.map(adminDTO, Admin.class);

        return admin;
    }
}
