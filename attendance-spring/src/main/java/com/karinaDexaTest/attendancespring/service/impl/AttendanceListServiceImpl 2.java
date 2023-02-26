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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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
    @Transactional
    public AddAttendanceListResponseDTO addEmployeeAttendanceList(AddAttendanceListRequestDTO dto) {
        Boolean validateEmployeeIdIsExist = webClient.get()
                .uri("http://localhost:8080/employee/validate/" + dto.getEmployeeId())
                .retrieve()
                .bodyToMono(boolean.class)
                .block();

        if(!validateEmployeeIdIsExist) {
            return AddAttendanceListResponseDTO.builder()
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

        return AddAttendanceListResponseDTO.builder()
                .isSuccess(true)
                .message("Employee succeed to add their attendance")
                .attendanceListDTO(attendanceListConverter.toDTO(attendanceList))
                .isWorkAttendance(dto.isWorkAttendance())
                .build();
    }

    @Override
    public GetEmployeeAttendanceListResponseDTO getSpecificEmployeeAttendance(GetEmployeeAttendanceListRequestDTO dto) {
        Boolean validateEmployeeIdIsExist = webClient.get()
                .uri("http://localhost:8080/employee/validate/" + dto.getEmployeeId())
                .retrieve()
                .bodyToMono(boolean.class)
                .block();

        if(!validateEmployeeIdIsExist) {
            return GetEmployeeAttendanceListResponseDTO.builder()
                    .message("Get employee attendance was failed because employee doesn't exist")
                    .isSuccess(false)
                    .build();
        }

        ZoneId zoneId = ZoneId.of("Asia/Bangkok");

        ZonedDateTime startDate = dto.getStartAt();
        ZonedDateTime endDate = dto.getEndAt();

        LocalDateTime currentTime = LocalDateTime.now();

        if(startDate == null) startDate = currentTime.with(TemporalAdjusters.firstDayOfMonth()).atZone(zoneId);
        if(endDate == null) endDate = currentTime.atZone(zoneId);

        Page<AttendanceList> attendanceLists = attendanceListRepository.findAllByIdAndCreated(dto.getEmployeeId(), startDate, endDate, Pageable.ofSize(MAX_TOTAL_ATTENDANCE_LIST_SHOW));

        return GetEmployeeAttendanceListResponseDTO.builder()
                .isSuccess(true)
                .attendanceListDTOS(attendanceListConverter.convertToDtoList(attendanceLists))
                .message("Get employee attendance list is succeed")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceListDTO> getAllEmployeeAttendance() {
        Page<AttendanceList> attendanceLists = attendanceListRepository.getAllWithPageable(Pageable.ofSize(MAX_TOTAL_ATTENDANCE_LIST_SHOW));

        return attendanceListConverter.convertToDtoList(attendanceLists);
    }
}
