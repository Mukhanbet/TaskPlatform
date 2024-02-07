package com.example.taskPlatform.mapper.impl;

import com.example.taskPlatform.dto.task.TaskResponse;
import com.example.taskPlatform.dto.user.UserResponse;
import com.example.taskPlatform.entities.Task;
import com.example.taskPlatform.entities.User;
import com.example.taskPlatform.mapper.TaskMapper;
import com.example.taskPlatform.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskResponse toDto(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setName(task.getName());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setCreatedDay(task.getCreatedDay());
        taskResponse.setAuthor(task.getUser().getName());
        taskResponse.setSolver(task.getSolver());
        taskResponse.setLevel(task.getTaskLevel().getLevel());
//        if(!task.isAvailable()) {
//            taskResponse.setStatus("Canceled");
//        } else if (task.isAvailable()) {
//            taskResponse.setStatus("Available");
//        } else {
//            taskResponse.setStatus("Not available");
//        }
        return taskResponse;
    }

    @Override
    public List<TaskResponse> toDtoS(List<Task> all) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : all) {
            taskResponses.add(toDto(task));
        }
        return taskResponses;
    }
}
