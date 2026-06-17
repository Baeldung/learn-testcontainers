package com.baeldung.ltc.service;

import com.baeldung.ltc.persistence.model.Task;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(Long id);

    Task save(Task task);
}
