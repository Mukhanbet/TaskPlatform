package com.example.taskPlatform.dto.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
}
