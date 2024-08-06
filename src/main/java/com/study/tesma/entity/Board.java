package com.study.tesma.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String boardName;

    private int userId;

    private Integer fileId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Transient
    private String writer;
}
