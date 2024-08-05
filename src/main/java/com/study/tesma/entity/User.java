package com.study.tesma.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    private String name;
    private int grade;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
