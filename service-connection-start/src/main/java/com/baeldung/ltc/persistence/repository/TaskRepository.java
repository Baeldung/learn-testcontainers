package com.baeldung.ltc.persistence.repository;

import com.baeldung.ltc.persistence.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
