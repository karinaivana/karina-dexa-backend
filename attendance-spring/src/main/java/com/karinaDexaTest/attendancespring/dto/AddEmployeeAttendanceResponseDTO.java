package com.karinaDexaTest.attendancespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Setter @Getter
public class AddEmployeeAttendanceResponseDTO {
    private boolean isSuccess;
    private String message;
    private boolean isWorkAttendance;
}
