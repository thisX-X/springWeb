package com.study.tesma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoginUser {
    @Id
    private int id;

    private String email;

    private String ip;

    private String loginTime;
}
