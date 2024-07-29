package com.study.tesma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    private int id;
    private int userId;
    private int boardId;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Setter
    @Getter
    @Transient
    private String writer;

}
