package com.example.taskPlatform.controllers;

import com.example.taskPlatform.dto.task.TaskRequest;
import com.example.taskPlatform.dto.task.TaskResponse;
import com.example.taskPlatform.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService service;

    @GetMapping("/getAll")
    public List<TaskResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/findByName/{name}")
    public TaskResponse findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name,@RequestBody TaskRequest taskRequest) {
        service.updateByName(name, taskRequest);
    }

    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @PostMapping("/create")
    public void create(@RequestBody TaskRequest taskRequest) {
        service.create(taskRequest);
    }
}
