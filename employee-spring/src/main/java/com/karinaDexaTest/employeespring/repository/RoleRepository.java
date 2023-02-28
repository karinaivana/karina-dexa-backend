package com.karinaDexaTest.employeespring.repository;

import com.karinaDexaTest.employeespring.model.Employee;
import com.karinaDexaTest.employeespring.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("Select r from Role r where r.deletedAt is null")
    Page<Role> getAllRoleNotDeleted(Pageable pageable);

    @Query("Select r from Role r where r.id = :id")
    Role getRoleByIdString(
            @Param("id") String id
    );
}
