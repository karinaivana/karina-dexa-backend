package com.karinaDexaTest.attendancespring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddAttendanceListRequestDTO {
    private long employeeId;
    private boolean isWorkAttendance;
}
