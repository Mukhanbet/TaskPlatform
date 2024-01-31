package com.example.taskPlatform.dto.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate createdDay;
    private String author;
    private String solver;
    private String level;
//    private String status;
}
