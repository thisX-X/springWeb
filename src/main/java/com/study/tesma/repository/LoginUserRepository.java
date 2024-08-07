package com.study.tesma.repository;

import com.study.tesma.entity.LoginUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM LoginUser lu WHERE lu.email = :email")
    void deleteByEmail(@Param("email") String email);
}
