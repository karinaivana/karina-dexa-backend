package com.karinaDexaTest.attendancespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder @Setter @Getter
public class GetAllEmployeeAttendanceResponseDTO {
    private boolean isSuccess;
    private List<AttendanceListDTO> attendanceListDTOS;
}
