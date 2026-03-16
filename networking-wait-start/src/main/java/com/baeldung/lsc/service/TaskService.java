package com.baeldung.lsc.service;

import java.util.Optional;

import com.baeldung.lsc.persistence.model.Task;

public interface TaskService {
    Optional<Task> findById(Long id);

    Task save(Task task);
}
