package com.example.taskPlatform.mapper.impl;

import com.example.taskPlatform.dto.task_level.TaskLevelResponse;
import com.example.taskPlatform.entities.TaskLevel;
import com.example.taskPlatform.mapper.TaskLevelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskLevelMapperImpl implements TaskLevelMapper {
    @Override
    public TaskLevelResponse toDto(TaskLevel taskLevel) {
        TaskLevelResponse taskLevelResponse = new TaskLevelResponse();
        taskLevelResponse.setId(taskLevel.getId());
        taskLevelResponse.setLevel(taskLevel.getLevel());
        taskLevelResponse.setPoint(taskLevel.getPoint());
        return taskLevelResponse;
    }

    @Override
    public List<TaskLevelResponse> toDtoS(List<TaskLevel> all) {
        List<TaskLevelResponse> taskLevelResponses = new ArrayList<>();
        for(TaskLevel taskLevel : all) {
            taskLevelResponses.add(toDto(taskLevel));
        }
        return taskLevelResponses;
    }
}
