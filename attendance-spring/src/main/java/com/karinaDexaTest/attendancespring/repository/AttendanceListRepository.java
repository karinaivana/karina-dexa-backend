package com.karinaDexaTest.attendancespring.repository;

import com.karinaDexaTest.attendancespring.model.AttendanceList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface AttendanceListRepository extends JpaRepository<AttendanceList, Long> {
    @Query("Select al from AttendanceList al " +
            "where al.employeeId = :ei " +
            "and createdAt between :sa and :ea " +
            "order by al.createdAt desc")
    Page<AttendanceList> findAllByIdAndCreated(
            @Param("ei") Long employeeId,
            @Param("sa") ZonedDateTime startAt,
            @Param("ea") ZonedDateTime endAt,
            Pageable pageable
    );

    @Query("Select al from AttendanceList al " +
            "order by al.createdAt desc")
    Page<AttendanceList> getAllWithPageable(
            Pageable pageable
    );

    @Query("Select al from AttendanceList al " +
            "where al.id = :id " +
            "and createdAt >= :sda")
    AttendanceList findByEmployeeIdToday(
            @Param("id") Long id,
            @Param("sda") ZonedDateTime startDayAt
    );
}
