package com.karinaDexaTest.attendancespring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter @Builder
public class GetEmployeeAttendanceListRequestDTO {
    private long employeeId;
    private ZonedDateTime startAt;
    private ZonedDateTime endAt;
}
