package com.study.tesma.repository;

import com.study.tesma.entity.SessionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionKeyRepository extends JpaRepository<SessionKey, String> {

}
