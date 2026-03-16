package com.baeldung.lsc.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsc.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
