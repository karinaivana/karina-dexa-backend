package com.karinaDexaTest.attendancespring.converter;

import com.karinaDexaTest.attendancespring.dto.AttendanceListDTO;
import com.karinaDexaTest.attendancespring.model.AttendanceList;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttendanceListConverter {
    private final ModelMapper modelMapper;

    public AttendanceListConverter(
            ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    public AttendanceListDTO toDTO(AttendanceList attendanceList) {
        if(attendanceList == null) return null;

        AttendanceListDTO attendanceListDTO = modelMapper.map(attendanceList, AttendanceListDTO.class);

        return attendanceListDTO;
    }

    public List<AttendanceListDTO> convertToDtoList(Page<AttendanceList> attendanceListPage) {
        List<AttendanceList> entities = attendanceListPage.getContent();
        return entities.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
    }

    public AttendanceList toEntity(AttendanceListDTO attendanceListDTO) {
        if(attendanceListDTO == null) return null;

        AttendanceList attendanceList = modelMapper.map(attendanceListDTO, AttendanceList.class);

        return attendanceList;
    }
}
