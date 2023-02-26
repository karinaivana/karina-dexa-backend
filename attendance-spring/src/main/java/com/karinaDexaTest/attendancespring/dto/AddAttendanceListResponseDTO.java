package com.karinaDexaTest.attendancespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Setter @Getter
public class AddAttendanceListResponseDTO {
    private boolean isSuccess;
    private String message;
    private AttendanceListDTO attendanceListDTO;
    private boolean isWorkAttendance;
}
