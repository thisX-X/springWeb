package com.study.tesma.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int boardId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
