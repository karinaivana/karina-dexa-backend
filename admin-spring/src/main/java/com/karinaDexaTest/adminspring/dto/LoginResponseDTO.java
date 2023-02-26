package com.karinaDexaTest.adminspring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Setter @Getter
public class LoginResponseDTO {
    private boolean isSuccess;
    private String message;
    private AdminDTO adminDTO;
}
