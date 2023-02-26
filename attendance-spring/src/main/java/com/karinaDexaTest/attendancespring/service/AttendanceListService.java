package com.karinaDexaTest.attendancespring.service;

import com.karinaDexaTest.attendancespring.dto.*;

import java.util.List;

public interface AttendanceListService {
    AddAttendanceListResponseDTO addEmployeeAttendanceList(AddAttendanceListRequestDTO dto);
    GetEmployeeAttendanceListResponseDTO getSpecificEmployeeAttendance(GetEmployeeAttendanceListRequestDTO dto);

    List<AttendanceListDTO> getAllEmployeeAttendance();
}
