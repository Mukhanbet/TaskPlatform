package com.example.taskPlatform.service;

import com.example.taskPlatform.dto.task_level.TaskLevelRequest;
import com.example.taskPlatform.dto.task_level.TaskLevelResponse;

import java.util.List;

public interface TaskLevelService {
    List<TaskLevelResponse> getAll();
    TaskLevelResponse findByLevel(String level);
    void updateByLevel(String level, TaskLevelRequest taskLevelRequest);
    void deleteByLevel(String level);
    void create(TaskLevelRequest taskLevelRequest);
}
