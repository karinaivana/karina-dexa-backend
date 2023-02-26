package com.karinaDexaTest.attendancespring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name="attendance_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceList {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "start_work_at")
    private ZonedDateTime startWorkAt;

    @Column(name = "end_work_at")
    private ZonedDateTime endWorkAt;
}
