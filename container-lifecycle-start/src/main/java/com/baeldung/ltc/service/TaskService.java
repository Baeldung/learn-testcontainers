package com.baeldung.ltc.service;

import java.util.Optional;

import com.baeldung.ltc.persistence.model.Task;

public interface TaskService {
    Optional<Task> findById(Long id);

    Task save(Task task);
}
