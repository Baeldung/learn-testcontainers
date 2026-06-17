package com.baeldung.ltc.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.ltc.persistence.model.Task;
import com.baeldung.ltc.persistence.repository.TaskRepository;
import com.baeldung.ltc.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

}
