package com.study.tesma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {
    @Id
    private int id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
