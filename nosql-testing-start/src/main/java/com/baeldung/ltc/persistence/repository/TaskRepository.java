package com.baeldung.ltc.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.ltc.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
