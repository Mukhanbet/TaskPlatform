package com.example.taskPlatform.controllers;

import com.example.taskPlatform.dto.task_level.TaskLevelRequest;
import com.example.taskPlatform.dto.task_level.TaskLevelResponse;
import com.example.taskPlatform.service.TaskLevelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/taskLevel")
public class TaskLevelController {
    private final TaskLevelService taskLevelService;

    @GetMapping("/getAll")
    public List<TaskLevelResponse> getAll() {
        return taskLevelService.getAll();
    }

    @GetMapping("/findByLevel/{level}")
    public TaskLevelResponse findByLevel(@PathVariable String level) {
        return taskLevelService.findByLevel(level);
    }

    @PutMapping("/updateByLevel/{level}")
    public void updateByLevel(@PathVariable String level, @RequestBody TaskLevelRequest taskLevelRequest) {
        taskLevelService.updateByLevel(level, taskLevelRequest);
    }

    @DeleteMapping("/deleteByLevel/{level}")
    public void deleteByLevel(@PathVariable String level) {
        taskLevelService.deleteByLevel(level);
    }

    @PostMapping("/create")
    public void create(@RequestBody TaskLevelRequest taskLevelRequest) {
        taskLevelService.create(taskLevelRequest);
    }
}
