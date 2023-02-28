package com.karinaDexaTest.attendancespring.service;

import com.karinaDexaTest.attendancespring.dto.*;

import java.util.List;

public interface AttendanceListService {
    ValidateEmployeeAttendanceTodayResponseDTO validateEmployeeAttendanceToday(long employeeId);
    AddEmployeeAttendanceResponseDTO addEmployeeAttendance(AddEmployeeAttendanceRequestDTO dto);
    GetEmployeeAttendanceListResponseDTO getSpecificEmployeeAttendance(GetEmployeeAttendanceListRequestDTO dto);
    List<AttendanceListDTO> getAllEmployeeAttendance();
}
