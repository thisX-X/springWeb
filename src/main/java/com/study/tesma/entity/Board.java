package com.study.tesma.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int boardId;

    private int userId;

    @Column(nullable = true)
    private Integer fileId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Setter
    @Getter
    @Transient
    private String writer;

    @Setter
    @Getter
    @Transient
    private String boardName;
}
