package com.karinaDexaTest.attendancespring.controller;

import com.karinaDexaTest.attendancespring.dto.*;
import com.karinaDexaTest.attendancespring.service.AttendanceListService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
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
    public ResponseEntity<AddAttendanceListResponseDTO> addEmployeeAttendanceList(
            @RequestBody AddAttendanceListRequestDTO addAttendanceListRequestDTO
    ) {
        AddAttendanceListResponseDTO addAttendanceListResponseDTO = attendanceListService.addEmployeeAttendanceList(addAttendanceListRequestDTO);

        return ResponseEntity.ok(addAttendanceListResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<GetEmployeeAttendanceListResponseDTO> getEmployeeAttendanceList(
            @RequestParam long employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endAt
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
}
