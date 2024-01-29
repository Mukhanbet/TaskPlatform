package com.example.taskPlatform.mapper;

import com.example.taskPlatform.dto.task.TaskResponse;
import com.example.taskPlatform.entities.Task;

import java.util.List;

public interface TaskMapper {
    TaskResponse toDto(Task task);
    List<TaskResponse> toDtoS(List<Task> all);
}
