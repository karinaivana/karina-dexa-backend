package com.karinaDexaTest.attendancespring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddEmployeeAttendanceRequestDTO {
    private long employeeId;
    private boolean isWorkAttendance;
}
