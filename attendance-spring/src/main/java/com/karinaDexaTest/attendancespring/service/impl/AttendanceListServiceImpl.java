package com.karinaDexaTest.attendancespring.service.impl;

import com.karinaDexaTest.attendancespring.converter.AttendanceListConverter;
import com.karinaDexaTest.attendancespring.dto.*;
import com.karinaDexaTest.attendancespring.model.AttendanceList;
import com.karinaDexaTest.attendancespring.repository.AttendanceListRepository;
import com.karinaDexaTest.attendancespring.service.AttendanceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Service
public class AttendanceListServiceImpl implements AttendanceListService {
    private final AttendanceListRepository attendanceListRepository;
    private final AttendanceListConverter attendanceListConverter;

    @Autowired
    private WebClient webClient;

    public AttendanceListServiceImpl(
            AttendanceListRepository attendanceListRepository,
            AttendanceListConverter attendanceListConverter
    ){
        this.attendanceListRepository = attendanceListRepository;
        this.attendanceListConverter = attendanceListConverter;
    }

    private final int MAX_TOTAL_ATTENDANCE_LIST_SHOW = 1000;

    @Override
    public ValidateEmployeeAttendanceTodayResponseDTO validateEmployeeAttendanceToday(long employeeId) {
        boolean isEmployeeAlreadyCome = false;

        ZoneId zoneId = ZoneId.of("Asia/Bangkok");
        LocalDateTime currentDateTime = LocalDateTime.now();

        ZonedDateTime currentDateTimeOnMidnight = currentDateTime.with(LocalTime.MIDNIGHT).atZone(zoneId);

        AttendanceList attendanceList = attendanceListRepository.findByEmployeeIdToday(employeeId, currentDateTimeOnMidnight);

        if(attendanceList != null && attendanceList.getCreatedAt() != null) isEmployeeAlreadyCome = true;

        return ValidateEmployeeAttendanceTodayResponseDTO.builder()
                .isSuccess(true)
                .message("Success get employee attendance track")
                .isEmployeeAlreadyCome(isEmployeeAlreadyCome)
                .build();
    }

    @Override
    @Transactional
    public AddEmployeeAttendanceResponseDTO addEmployeeAttendance(AddEmployeeAttendanceRequestDTO dto) {
        //call the employee service to validate whether the employee exists based on the employee_id
        Boolean validateEmployeeIdIsExist = webClient.get()
                .uri("http://localhost:8084/employee/validate/" + dto.getEmployeeId())
                .retrieve()
                .bodyToMono(boolean.class)
                .block();

        if(!validateEmployeeIdIsExist) {
            return AddEmployeeAttendanceResponseDTO.builder()
                    .message("Add employee attendance is failed because employee doesn't exist")
                    .isSuccess(false)
                    .build();
        }

        ZoneId zoneId = ZoneId.of("Asia/Bangkok");
        LocalDateTime currentDateTime = LocalDateTime.now();

        ZonedDateTime currentDateTimeOnMidnight = currentDateTime.with(LocalTime.MIDNIGHT).atZone(zoneId);

        AttendanceList attendanceList = attendanceListRepository.findByEmployeeIdToday(dto.getEmployeeId(), currentDateTimeOnMidnight);

        if(attendanceList == null) {
            attendanceList = new AttendanceList();
            attendanceList.setEmployeeId(dto.getEmployeeId());
        }

        if(dto.isWorkAttendance()) attendanceList.setStartWorkAt(currentDateTime.atZone(zoneId));
        else attendanceList.setEndWorkAt(currentDateTime.atZone(zoneId));

        attendanceListRepository.save(attendanceList);

        return AddEmployeeAttendanceResponseDTO.builder()
                .isSuccess(true)
                .message("Employee succeed to add their attendance")
                .isWorkAttendance(dto.isWorkAttendance())
                .build();
    }

    @Override
    public GetEmployeeAttendanceListResponseDTO getSpecificEmployeeAttendance(GetEmployeeAttendanceListRequestDTO dto) {
        //call the employee service to validate whether the employee exists based on the employee_id
        Boolean validateEmployeeIdIsExist = webClient.get()
                .uri("http://localhost:8084/employee/validate/" + dto.getEmployeeId())
                .retrieve()
                .bodyToMono(boolean.class)
                .block();

        if(!validateEmployeeIdIsExist) {
            return GetEmployeeAttendanceListResponseDTO.builder()
                    .message("Get employee attendance was failed because employee doesn't exist")
                    .isSuccess(false)
                    .build();
        }

        ZoneId idZoneId = ZoneId.of("Asia/Bangkok");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ZonedDateTime startDate;
        ZonedDateTime endDate;

        LocalDateTime currentTime = LocalDateTime.now();

        //Get startAt and endAt value based on input. If input == null use default setting.
        //default startAt: first day of the month
        //default endAt: current date time
        if(dto.getStartAt() == null || dto.getEndAt() == null) {
            startDate = currentTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIDNIGHT).atZone(idZoneId);
            endDate = currentTime.atZone(idZoneId);
        } else {
            startDate = LocalDate.parse(dto.getStartAt(), dtf).atStartOfDay(idZoneId);

            endDate = LocalDate.parse(dto.getEndAt(), dtf).plusDays(1).atStartOfDay(idZoneId);
        }

        Page<AttendanceList> attendanceLists = attendanceListRepository.findAllByIdAndCreated(dto.getEmployeeId(), startDate, endDate, Pageable.ofSize(MAX_TOTAL_ATTENDANCE_LIST_SHOW));

        return GetEmployeeAttendanceListResponseDTO.builder()
                .isSuccess(true)
                .attendanceListDTOS(attendanceListConverter.convertToDtoList(attendanceLists))
                .message("Get employee attendance list is succeed")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GetAllEmployeeAttendanceResponseDTO getAllEmployeeAttendance() {
        Page<AttendanceList> attendanceLists = attendanceListRepository.getAllWithPageable(Pageable.ofSize(MAX_TOTAL_ATTENDANCE_LIST_SHOW));

        return GetAllEmployeeAttendanceResponseDTO.builder()
                .isSuccess(true)
                .attendanceListDTOS(attendanceListConverter.convertToDtoList(attendanceLists))
                .build();
    }
}
