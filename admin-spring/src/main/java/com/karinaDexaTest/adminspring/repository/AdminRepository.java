package com.karinaDexaTest.adminspring.repository;

import com.karinaDexaTest.adminspring.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsernameAndPassword(String username, String password);

    @Query("Select a from Admin a where a.username = :username")
    Admin findExistingAdminByUserName(
            @Param("username") String username
    );
}
