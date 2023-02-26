package com.karinaDexaTest.adminspring.service;

import com.karinaDexaTest.adminspring.dto.*;
import com.karinaDexaTest.adminspring.model.Admin;

public interface AdminService {
    LoginResponseDTO login(LoginRequestDTO dto);
    CreateNewAdminResponseDTO createNewAdmin(CreateNewAdminRequestDTO dto);
}
