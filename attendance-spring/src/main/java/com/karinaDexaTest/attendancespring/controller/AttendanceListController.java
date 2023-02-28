package com.karinaDexaTest.attendancespring.controller;

import com.karinaDexaTest.attendancespring.dto.*;
import com.karinaDexaTest.attendancespring.service.AttendanceListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceListController {
    private final AttendanceListService attendanceListService;

    public AttendanceListController(
        AttendanceListService attendanceListService
    ) {
        this.attendanceListService = attendanceListService;
    }

    @PostMapping("/add")
    public ResponseEntity<AddEmployeeAttendanceResponseDTO> addEmployeeAttendance(
            @RequestBody AddEmployeeAttendanceRequestDTO addEmployeeAttendanceRequestDTO
    ) {
        AddEmployeeAttendanceResponseDTO addEmployeeAttendanceResponseDTO = attendanceListService.addEmployeeAttendance(addEmployeeAttendanceRequestDTO);

        return ResponseEntity.ok(addEmployeeAttendanceResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<GetEmployeeAttendanceListResponseDTO> getEmployeeAttendanceList(
            @RequestParam long employeeId,
            @RequestParam(required = false) String startAt,
            @RequestParam(required = false) String endAt
    ) {
        GetEmployeeAttendanceListRequestDTO getEmployeeAttendanceListRequestDTO = GetEmployeeAttendanceListRequestDTO.builder()
                .employeeId(employeeId)
                .startAt(startAt)
                .endAt(endAt)
                .build();

        GetEmployeeAttendanceListResponseDTO getEmployeeAttendanceListResponseDTO = attendanceListService.getSpecificEmployeeAttendance(getEmployeeAttendanceListRequestDTO);

        return ResponseEntity.ok(getEmployeeAttendanceListResponseDTO);
    }

    @GetMapping("/all/list")
    public ResponseEntity<List<AttendanceListDTO>> getAllEmployeeAttendance() {
        List<AttendanceListDTO> attendanceListDTOS = attendanceListService.getAllEmployeeAttendance();

        return ResponseEntity.ok(attendanceListDTOS);
    }

    @GetMapping("/validate/today")
    public ResponseEntity<ValidateEmployeeAttendanceTodayResponseDTO> validateEmployeeAttendanceToday(
            @RequestParam long employeeId
    ) {
        ValidateEmployeeAttendanceTodayResponseDTO attendanceListDTOS = attendanceListService.validateEmployeeAttendanceToday(employeeId);

        return ResponseEntity.ok(attendanceListDTOS);
    }
}
