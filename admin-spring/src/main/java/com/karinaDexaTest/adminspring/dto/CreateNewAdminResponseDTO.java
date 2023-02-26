package com.karinaDexaTest.adminspring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class CreateNewAdminResponseDTO {
    private boolean isSuccess;
    private String message;
    private AdminDTO adminDTO;
}
