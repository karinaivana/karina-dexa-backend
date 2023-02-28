package com.karinaDexaTest.attendancespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class ValidateEmployeeAttendanceTodayResponseDTO {
    private boolean isSuccess;
    private String message;
    private boolean isEmployeeAlreadyCome;
}
