package com.example.taskPlatform.repositories;

import com.example.taskPlatform.entities.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Transactional
    void deleteByName(String name);
    Optional<Task> findByName(String name);
}
