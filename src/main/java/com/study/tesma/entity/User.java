package com.study.tesma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class User {
    @Id
    private int id;
    private String email;
    private String password;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
