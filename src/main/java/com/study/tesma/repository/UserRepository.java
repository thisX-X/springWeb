package com.study.tesma.repository;

import com.study.tesma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    Object findNameById(int userId);
}
