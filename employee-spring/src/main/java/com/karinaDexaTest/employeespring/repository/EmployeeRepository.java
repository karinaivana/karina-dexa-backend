package com.karinaDexaTest.employeespring.repository;

import com.karinaDexaTest.employeespring.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmailAndPassword(String email, String password);

    @Query("Select e from Employee e where e.id = :id")
    Employee findExistingEmployee(
            @Param("id") Long id
    );

    @Query("Select e from Employee e where e.email = :email")
    Employee findExistingEmployeeByEmail(
            @Param("email") String email
    );
}
