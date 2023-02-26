package com.karinaDexaTest.attendancespring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter @Setter
public class AttendanceListDTO {
    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime deletedAt;
    private Long employeeId;
    private ZonedDateTime startWorkAt;
    private ZonedDateTime endWorkAt;
}
