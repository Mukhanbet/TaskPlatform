package com.example.taskPlatform.mapper;

import com.example.taskPlatform.dto.task_level.TaskLevelResponse;
import com.example.taskPlatform.entities.TaskLevel;

import java.util.List;

public interface TaskLevelMapper {
    TaskLevelResponse toDto(TaskLevel taskLevel);
    List<TaskLevelResponse> toDtoS(List<TaskLevel> all);
}
