package com.karinaDexaTest.attendancespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder @Getter @Setter
public class GetEmployeeAttendanceListResponseDTO {
    private boolean isSuccess;
    private String message;
    private List<AttendanceListDTO> attendanceListDTOS;
}
